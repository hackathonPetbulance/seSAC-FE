package com.example.presentation.screen.home

import com.example.domain.model.feature.hospitals.HospitalCard
import com.example.domain.model.feature.reviews.HospitalReview
import com.example.domain.usecase.feature.hospital.GetNearByHospitalUseCase
import com.example.presentation.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNearByHospitalUseCase: GetNearByHospitalUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Init)
    val state: StateFlow<HomeState> = _state

    private val _eventFlow = MutableSharedFlow<HomeEvent>()
    val eventFlow: SharedFlow<HomeEvent> = _eventFlow

    private val _hospitalCards = MutableStateFlow(emptyList<HospitalCard>())
    val hospitalCards: StateFlow<List<HospitalCard>> = _hospitalCards

    private val _hospitalReviews = MutableStateFlow(emptyList<HospitalReview>())
    val hospitalReviews: StateFlow<List<HospitalReview>> = _hospitalReviews


    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.SomeIntentWithoutParams -> {
                //do sth
            }

            is HomeIntent.SomeIntentWithParams -> {
                //do sth(intent.params)
            }
        }
    }

    init {
        launch {
            getNearByHospital()
        }
    }

    private suspend fun getNearByHospital() {
        _state.value = HomeState.OnProgress
        runCatching {
            getNearByHospitalUseCase()
        }.onSuccess { result ->
            _hospitalCards.value = result.getOrThrow()
        }.onFailure { ex ->
            _eventFlow.emit(
                HomeEvent.DataFetch.Error(
                    userMessage = "근처 병원 조회에 실패했어요.",
                    exceptionMessage = ex.message
                )
            )
        }
        _state.value = HomeState.Init
    }

}
