package com.example.presentation.screen.diagnosis.report

import com.example.domain.model.feature.diagnosis.AiDiagnosis
import com.example.domain.model.feature.hospitals.HospitalCard

data class ReportData(
    val aiDiagnosis: AiDiagnosis,
    val userLocation: String,
    val matchedHospitals: List<HospitalCard>

    ) {
    companion object {
        fun empty() = ReportData(
            AiDiagnosis.empty(),
            userLocation = "서울 마포구",
            matchedHospitals = emptyList()
        )
    }
}