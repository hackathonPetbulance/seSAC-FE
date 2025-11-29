package com.example.presentation.screen.diagnosis

import android.net.Uri
import android.util.Log
import com.example.domain.model.feature.diagnosis.AiDiagnosis
import com.example.domain.usecase.feature.diagnosis.RequestDiagnosisUseCase
import com.example.presentation.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Is shared by DiagnosisScreen and Report Screen
 */
@HiltViewModel
class DiagnosisViewModel @Inject constructor(
    private val requestDiagnosisUseCase: RequestDiagnosisUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow<DiagnosisState>(DiagnosisState.Init)
    val state: StateFlow<DiagnosisState> = _state

    private val _eventFlow = MutableSharedFlow<DiagnosisEvent>(replay = 1)
    val eventFlow: SharedFlow<DiagnosisEvent> = _eventFlow

    private val _aiDiagnosis = MutableStateFlow<AiDiagnosis?>(null)
    val aiDiagnosis: StateFlow<AiDiagnosis?> = _aiDiagnosis

    private val _imageUris = MutableStateFlow<List<Uri>>(emptyList())
    val imageUris: StateFlow<List<Uri>> = _imageUris

    private val _animalSpecies = MutableStateFlow("")
    val animalSpecies: StateFlow<String> = _animalSpecies

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    fun onIntent(intent: DiagnosisIntent) {
        when (intent) {
            is DiagnosisIntent.UpdateAnimalSpecies -> {
                _animalSpecies.value = intent.species
            }
            is DiagnosisIntent.UpdateDescription -> {
                _description.value = intent.description
            }
            is DiagnosisIntent.UpdateImageUris -> {
                    _imageUris.value = intent.uris

            }
            is DiagnosisIntent.RequestDiagnosis -> {
                launch {
                    requestDiagnosis(onUpload = intent.onUpload)
                }
            }
        }
    }

    private suspend fun requestDiagnosis(onUpload: (Long, Long) -> Unit) {
        _state.value = DiagnosisState.OnProgress
        Log.d("siria22", "Request Diagnosis Request images : \n${_imageUris.value.map { it.toString() }}")
        runCatching {
            requestDiagnosisUseCase(
                images = _imageUris.value.take(3).map { it.toString() },
                animalType = _animalSpecies.value,
                symptom = _description.value,
                onUpload = onUpload
            )
        }.onSuccess { result ->
            Log.d("siria22", "Request Sent\n" +
                    "${_imageUris.value.map { it.toString() }}, \n" +
                    "${_animalSpecies.value}, \n" +
                    _description.value
            )
            result.getOrNull()?.let {
                _aiDiagnosis.value = it
                _eventFlow.emit(DiagnosisEvent.RequestSuccess)
            }
        }.onFailure { exception ->
            _eventFlow.emit(
                DiagnosisEvent.DataFetch.Error(
                    userMessage = "진단 요청 중 오류가 발생했습니다.",
                    exceptionMessage = exception.message
                )
            )
        }
        _state.value = DiagnosisState.Init
    }
}