package com.example.presentation.screen.diagnosis

import androidx.lifecycle.SavedStateHandle
import com.example.presentation.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DiagnosisViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    // private val someUseCase: SomeUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow<DiagnosisState>(DiagnosisState.Init)
    val state: StateFlow<DiagnosisState> = _state

    private val _eventFlow = MutableSharedFlow<DiagnosisEvent>(replay = 1)
    val eventFlow: SharedFlow<DiagnosisEvent> = _eventFlow

    fun onIntent(intent: DiagnosisIntent) {
        when (intent) {
            is DiagnosisIntent.SomeIntentWithoutParams -> {
                //do sth
            }

            is DiagnosisIntent.SomeIntentWithParams -> {
                //do sth(intent.params)
            }
        }
    }

    init {
        launch {

        }
    }

    // some function
    private suspend fun someFunction() {
        _state.value = DiagnosisState.OnProgress

        runCatching {
            // someUseCase()
        }.onSuccess { result ->
            // some.value = result.getOrThrow()
        }.onFailure { exception ->
            // some.value = emptyList()
            _eventFlow.emit(
                DiagnosisEvent.DataFetch.Error(
                    userMessage = "Error messages to be shown to users",
                    exceptionMessage = exception.message
                )
            )
        }
        _state.value = DiagnosisState.Init
    }
}
