package com.example.presentation.screen.hospital

import androidx.lifecycle.SavedStateHandle
import com.example.presentation.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HospitalViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    // private val someUseCase: SomeUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow<HospitalState>(HospitalState.Init)
    val state: StateFlow<HospitalState> = _state

    private val _eventFlow = MutableSharedFlow<HospitalEvent>(replay = 1)
    val eventFlow: SharedFlow<HospitalEvent> = _eventFlow

    fun onIntent(intent: HospitalIntent) {
        when (intent) {
            is HospitalIntent.SomeIntentWithoutParams -> {
                //do sth
            }

            is HospitalIntent.SomeIntentWithParams -> {
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
        _state.value = HospitalState.OnProgress

        runCatching {
            // someUseCase()
        }.onSuccess { result ->
            // some.value = result.getOrThrow()
        }.onFailure { exception ->
            // some.value = emptyList()
            _eventFlow.emit(
                HospitalEvent.DataFetch.Error(
                    userMessage = "Error messages to be shown to users",
                    exceptionMessage = exception.message
                )
            )
        }
        _state.value = HospitalState.Init
    }
}
