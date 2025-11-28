package com.example.presentation.screen.diagnosis

data class DiagnosisData(
    val data: String
) {
    companion object {
        fun empty() = DiagnosisData(
            data = ""
        )
    }
}