package com.example.presentation.screen.diagnosis.report

import com.example.domain.model.feature.diagnosis.AiDiagnosis

data class ReportData(
    val aiDiagnosis: AiDiagnosis
) {
    companion object {
        fun empty() = ReportData(AiDiagnosis.empty())
    }
}