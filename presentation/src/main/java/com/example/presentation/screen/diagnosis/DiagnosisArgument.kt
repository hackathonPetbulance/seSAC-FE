package com.example.presentation.screen.diagnosis

import com.example.presentation.utils.error.ErrorEvent
import kotlinx.coroutines.flow.SharedFlow

data class DiagnosisArgument(
    val intent: (DiagnosisIntent) -> Unit,
    val state: DiagnosisState,
    val event: SharedFlow<DiagnosisEvent>
)

sealed class DiagnosisState {
    data object Init : DiagnosisState()
    data object OnProgress : DiagnosisState()
}

sealed class DiagnosisIntent {
    data class SomeIntentWithParams(val param: String) : DiagnosisIntent()
    data object SomeIntentWithoutParams : DiagnosisIntent()
}

sealed class DiagnosisEvent {
    sealed class DataFetch : DiagnosisEvent() {
        data class Error(
            override val userMessage: String = "문제가 발생했습니다.",
            override val exceptionMessage: String?
        ) : DataFetch(), ErrorEvent
    }
}