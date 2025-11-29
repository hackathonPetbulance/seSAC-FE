package com.example.presentation.screen.diagnosis.report

import com.example.presentation.utils.error.ErrorEvent
import kotlinx.coroutines.flow.SharedFlow

data class ReportArgument(
    val intent: (ReportIntent) -> Unit,
    val state: ReportState,
    val event: SharedFlow<ReportEvent>
)

sealed class ReportState {
    data object Init : ReportState()
    data object OnProgress : ReportState()
}

sealed class ReportIntent {
    data class SomeIntentWithParams(val param: String) : ReportIntent()
    data object SomeIntentWithoutParams : ReportIntent()
}

sealed class ReportEvent {
    sealed class DataFetch : ReportEvent() {
        data class Error(
            override val userMessage: String = "문제가 발생했습니다.",
            override val exceptionMessage: String?
        ) : DataFetch(), ErrorEvent
    }
}