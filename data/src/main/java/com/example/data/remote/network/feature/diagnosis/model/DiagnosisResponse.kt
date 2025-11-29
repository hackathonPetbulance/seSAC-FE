package com.example.data.remote.network.feature.diagnosis.model

import kotlinx.serialization.Serializable

@Serializable
data class DiagnosisResponse(
    val status: Int,
    val success: Boolean,
    val data: DiagnosisData
)

@Serializable
data class DiagnosisData(
    val emergencyLevel: String,
    val detectedSymptoms: List<String>,
    val suspectedDisease: String,
    val recommendedActions: List<String>
)