package com.example.presentation.screen.hospital

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.SavedStateHandle
import com.example.domain.model.feature.hospitals.HospitalDetail
import com.example.domain.model.feature.review.ReviewList
import com.example.domain.usecase.feature.hospital.GetDetailHospitalInfoUseCase
import com.example.domain.usecase.feature.review.GetReviewsUseCase
import com.example.presentation.utils.BaseViewModel
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HospitalViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDetailHospitalInfoUseCase: GetDetailHospitalInfoUseCase,
    private val getReviewsUseCase: GetReviewsUseCase,
    application: Application
) : BaseViewModel() {

    private val defaultLocation = Location("default").apply {
        latitude = 37.5665
        longitude = 126.9780
    }

    private val hospitalId: Long = savedStateHandle["hospitalId"] ?: 0L

    private val _currentUserLocation = MutableStateFlow(defaultLocation)
    val currentUserLocation: StateFlow<Location> = _currentUserLocation

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    private val _state = MutableStateFlow<HospitalState>(HospitalState.Init)
    val state: StateFlow<HospitalState> = _state

    private val _eventFlow = MutableSharedFlow<HospitalEvent>(replay = 1)
    val eventFlow: SharedFlow<HospitalEvent> = _eventFlow

    private val _hospital = MutableStateFlow<HospitalDetail?>(null)
    val hospital: StateFlow<HospitalDetail?> = _hospital

    private val _reviews = MutableStateFlow<ReviewList?>(null)
    val reviews: StateFlow<ReviewList?> = _reviews


    fun onIntent(intent: HospitalIntent) {}

    init {
        launch {
            checkAndFetchLocation(application.applicationContext)
        }
    }

    @SuppressLint("MissingPermission")
    private fun checkAndFetchLocation(context: Context) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("siria22", "Hospital VM - Has location permission")
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                launch {
                    val currentLocation = location ?: defaultLocation
                    _currentUserLocation.value = currentLocation
                    getDetailHospitalInfo(currentLocation.latitude, currentLocation.longitude)
                }
            }.addOnFailureListener {
                launch {
                    _eventFlow.emit(
                        HospitalEvent.DataFetch.Error(
                            userMessage = "위치 정보를 가져오는 데 실패했습니다.",
                            exceptionMessage = it.message
                        )
                    )
                }
            }
        } else {
            launch {
                _eventFlow.emit(
                    HospitalEvent.PermissionCheck.NoPermissionGranted(
                        exceptionMessage = "Location permission not granted."
                    )
                )
            }
        }
    }

    private suspend fun getDetailHospitalInfo(userLat: Double, userLng: Double) {
        _state.value = HospitalState.OnProgress

        runCatching {
            getDetailHospitalInfoUseCase(
                hospitalId = hospitalId,
                userLat = userLat,
                userLng = userLng
            )
        }.onSuccess { result ->
            Log.d("siria22", "getDetailHospitalInfo($userLat, $userLng) success:\n" +
                    "result : $result")
            _hospital.value = result
            getReviews()
        }.onFailure { exception ->
            _eventFlow.emit(
                HospitalEvent.DataFetch.Error(
                    userMessage = "병원 상세 정보 조회에 실패했습니다.",
                    exceptionMessage = exception.message
                )
            )
        }
        _state.value = HospitalState.Init
    }

    private suspend fun getReviews() {
        _state.value = HospitalState.OnProgress
        runCatching {
            getReviewsUseCase(_hospital.value?.name, null, null, null, null)
        }.onSuccess { result ->
            Log.d("siria22", "getReviews success:\nresult : $result")
            _reviews.value = result
        }.onFailure { ex ->
            _eventFlow.emit(
                HospitalEvent.DataFetch.Error(
                    userMessage = "리뷰 정보를 불러오는데 실패했습니다.",
                    exceptionMessage = ex.message
                )
            )
        }
        _state.value = HospitalState.Init
    }
}