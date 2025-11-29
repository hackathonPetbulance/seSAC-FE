package com.example.presentation.screen.diagnosis.report

import androidx.lifecycle.SavedStateHandle
import com.example.presentation.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    // private val someUseCase: SomeUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow<ReportState>(ReportState.Init)
    val state: StateFlow<ReportState> = _state

    private val _eventFlow = MutableSharedFlow<ReportEvent>(replay = 1)
    val eventFlow: SharedFlow<ReportEvent> = _eventFlow

    fun onIntent(intent: ReportIntent) {
        when (intent) {
            is ReportIntent.SomeIntentWithoutParams -> {
                //do sth
            }

            is ReportIntent.SomeIntentWithParams -> {
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
        _state.value = ReportState.OnProgress

        runCatching {
            // someUseCase()
        }.onSuccess { result ->
            // some.value = result.getOrThrow()
        }.onFailure { exception ->
            // some.value = emptyList()
            _eventFlow.emit(
                ReportEvent.DataFetch.Error(
                    userMessage = "Error messages to be shown to users",
                    exceptionMessage = exception.message
                )
            )
        }
        _state.value = ReportState.Init
    }
}
