package com.example.presentation.screen.report

data class ReportData(
    val data: String
) {
    companion object {
        fun empty() = ReportData(
            data = ""
        )
    }
}