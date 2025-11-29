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
    val animalType: String,
    val emergencyLevel: String,
    val detectedSymptoms: List<String>,
    val suspectedDisease: String,
    val totalSteps: Int,
    val steps: List<FirstAidGuideContentResponse>,
    val recommendedActions: List<String>,
    val confidence: Float
)

@Serializable
data class FirstAidGuideContentResponse(
    val description: String,
    val warning: String
)