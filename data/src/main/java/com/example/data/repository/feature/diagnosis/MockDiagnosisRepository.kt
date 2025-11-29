package com.example.data.repository.feature.diagnosis

import com.example.domain.model.feature.diagnosis.AiDiagnosis
import com.example.domain.model.type.EmergencyLevel
import com.example.domain.repository.feature.diagnosis.DiagnosisRepository
import javax.inject.Inject

class MockDiagnosisRepository @Inject constructor() : DiagnosisRepository {
    override suspend fun requestDiagnosis(
        images: List<String>,
        animalType: String,
        symptom: String,
        onUpload: (Long, Long) -> Unit
    ): Result<AiDiagnosis> {
        return Result.success(
            AiDiagnosis(
                emergencyLevel = EmergencyLevel.MIDDLE,
                detectedSymptoms = listOf(
                    "식욕 저하",
                    "활동성 감소",
                    "복부 팽만"
                ),
                suspectedDisease = "",
                recommendedActions = listOf(
                    "2시간 이내 전문의 진료 권장",
                    "이동 중 보온 유지 필수",
                    "강제 급식 금지"
                )
            )
        )
    }
}