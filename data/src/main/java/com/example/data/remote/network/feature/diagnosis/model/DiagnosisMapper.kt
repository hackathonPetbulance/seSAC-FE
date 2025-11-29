package com.example.data.remote.network.feature.diagnosis.model

import com.example.domain.model.feature.diagnosis.AiDiagnosis
import com.example.domain.model.feature.diagnosis.FirstAidGuide
import com.example.domain.model.feature.diagnosis.FirstAidGuideContent
import com.example.domain.model.type.EmergencyLevel

fun DiagnosisResponse.toDomain(): AiDiagnosis {
    val data = this.data
    return AiDiagnosis(
        animalType = data.animalType,
        emergencyLevel = when (data.emergencyLevel.uppercase()) {
            "HIGH" -> EmergencyLevel.HIGH
            "MIDDLE" -> EmergencyLevel.MIDDLE
            "LOW" -> EmergencyLevel.LOW
            else -> EmergencyLevel.MIDDLE
        },
        detectedSymptoms = data.detectedSymptoms,
        suspectedDisease = data.suspectedDisease,
        recommendedActions = data.recommendedActions,
        firstAidGuide = FirstAidGuide(
            totalSteps = data.totalSteps,
            steps = data.steps.map { it.toDomain() }
        ),
        confidence = data.confidence
    )
}

fun FirstAidGuideContentResponse.toDomain() = FirstAidGuideContent(
    description = this.description,
    warning = this.warning
)