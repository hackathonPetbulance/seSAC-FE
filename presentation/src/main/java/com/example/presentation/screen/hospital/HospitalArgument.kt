package com.example.presentation.screen.hospital

import com.example.presentation.utils.error.ErrorEvent
import kotlinx.coroutines.flow.SharedFlow

data class HospitalArgument(
    val intent: (HospitalIntent) -> Unit,
    val state: HospitalState,
    val event: SharedFlow<HospitalEvent>
)

sealed class HospitalState {
    data object Init : HospitalState()
    data object OnProgress : HospitalState()
}

sealed class HospitalIntent {
    data class SomeIntentWithParams(val param: String) : HospitalIntent()
    data object SomeIntentWithoutParams : HospitalIntent()
}

sealed class HospitalEvent {
    sealed class DataFetch : HospitalEvent() {
        data class Error(
            override val userMessage: String = "문제가 발생했습니다.",
            override val exceptionMessage: String?
        ) : DataFetch(), ErrorEvent
    }
}