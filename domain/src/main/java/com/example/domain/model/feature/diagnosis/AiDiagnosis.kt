package com.example.domain.model.feature.diagnosis

import com.example.domain.model.type.EmergencyLevel

data class AiDiagnosis(
    val emergencyLevel: EmergencyLevel,
    val detectedSymptoms: List<String>,
    val suspectedDisease: String,
    val recommendedActions: List<String>
) {
    companion object {
        fun empty() = AiDiagnosis(
            emergencyLevel = EmergencyLevel.LOW,
            detectedSymptoms = emptyList(),
            suspectedDisease = "",
            recommendedActions = emptyList()
        )
    }
}