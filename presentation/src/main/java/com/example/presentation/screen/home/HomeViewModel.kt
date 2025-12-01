package com.example.presentation.screen.home

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import com.example.domain.model.feature.hospitals.MatchedHospital
import com.example.domain.model.feature.review.ReviewList
import com.example.domain.usecase.feature.hospital.GetNearByHospitalUseCase
import com.example.domain.usecase.feature.review.GetReviewsUseCase
import com.example.domain.utils.zip
import com.example.presentation.utils.BaseViewModel
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNearByHospitalUseCase: GetNearByHospitalUseCase,
    private val getReviewsUseCase: GetReviewsUseCase,
    application: Application
) : BaseViewModel() {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    private val _state = MutableStateFlow<HomeState>(HomeState.Init)
    val state: StateFlow<HomeState> = _state

    private val _eventFlow = MutableSharedFlow<HomeEvent>()
    val eventFlow: SharedFlow<HomeEvent> = _eventFlow

    private val _matchedHospitalCards = MutableStateFlow(emptyList<MatchedHospital>())
    val matchedHospitalCards: StateFlow<List<MatchedHospital>> = _matchedHospitalCards

    private val _hospitalReviews = MutableStateFlow(ReviewList.empty())
    val hospitalReviews: StateFlow<ReviewList> = _hospitalReviews


    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.GetNearByHospital -> {
                launch {
                    getNearByHospitalAndReviews()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun getNearByHospitalAndReviews() {
        _state.value = HomeState.OnProgress

        val locationResult = runCatching {
            fusedLocationClient.getCurrentLocation(
                com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).await()
        }

        Log.d("siria22", "Fused location client settled")
        locationResult.onSuccess { location ->
            runCatching {
                zip(
                    { getNearByHospitalUseCase(location.latitude, location.longitude) },
                    { getReviewsUseCase(null, null, null, null, null) }
                )
            }.onSuccess { (hospitalList, reviewList) ->
                Log.d("siria22", "getNearByHospitalAndReviews success: \n" +
                        "hospitalList : ${hospitalList.size} items,\n" +
                        "reviewList : ${reviewList.list.size} items")
                _matchedHospitalCards.value = hospitalList
                _hospitalReviews.value = reviewList
            }.onFailure { exception ->
                Log.e("siria22", "getNearByHospitalAndReviews failed", exception)
                _eventFlow.emit(
                    HomeEvent.DataFetch.Error(
                        userMessage = "병원 정보를 가져오는 데 실패했습니다.",
                        exceptionMessage = exception.message
                    )
                )
            }
        }
        _state.value = HomeState.Init
    }
}