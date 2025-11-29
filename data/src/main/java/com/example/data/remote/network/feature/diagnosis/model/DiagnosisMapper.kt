package com.example.data.remote.network.feature.diagnosis.model

import com.example.domain.model.feature.diagnosis.AiDiagnosis
import com.example.domain.model.type.EmergencyLevel

fun DiagnosisResponse.toDomain() = AiDiagnosis(
    emergencyLevel = when (this.data.emergencyLevel.uppercase()) {
        "HIGH" -> EmergencyLevel.HIGH
        "MIDDLE" -> EmergencyLevel.MIDDLE
        "LOW" -> EmergencyLevel.LOW
        else -> EmergencyLevel.MIDDLE
    },
    detectedSymptoms = this.data.detectedSymptoms,
    suspectedDisease = this.data.suspectedDisease,
    recommendedActions = this.data.recommendedActions
)