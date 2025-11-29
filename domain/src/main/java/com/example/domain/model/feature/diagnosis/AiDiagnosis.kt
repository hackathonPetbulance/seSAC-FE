package com.example.domain.model.feature.diagnosis

import com.example.domain.model.type.EmergencyLevel

data class AiDiagnosis(
    val animalType: String,
    val emergencyLevel: EmergencyLevel,
    val detectedSymptoms: List<String>,
    val suspectedDisease: String,
    val recommendedActions: List<String>,
    val firstAidGuide: FirstAidGuide,
    val confidence: Float
) {
    companion object {
        fun empty() = AiDiagnosis(
            animalType = "",
            emergencyLevel = EmergencyLevel.LOW,
            detectedSymptoms = emptyList(),
            suspectedDisease = "",
            recommendedActions = emptyList(),
            firstAidGuide = FirstAidGuide(totalSteps = 0, steps = emptyList()),
            confidence = 0f
        )
    }
}

data class FirstAidGuide(
    val totalSteps: Int,
    val steps: List<FirstAidGuideContent>
)

data class FirstAidGuideContent(
    val description: String,
    val warning: String
)