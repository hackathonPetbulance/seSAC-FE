package com.example.data.repository.feature.diagnosis

import com.example.domain.model.feature.diagnosis.AiDiagnosis
import com.example.domain.model.feature.diagnosis.FirstAidGuide
import com.example.domain.model.feature.diagnosis.FirstAidGuideContent
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
                animalType = "앵무새",
                emergencyLevel = EmergencyLevel.MIDDLE,
                detectedSymptoms = listOf("식욕 저하", "활동성 감소", "이거 난가?"),
                suspectedDisease = "위장관 울혈 증후군 의심",
                recommendedActions = listOf(
                    "2시간 이내 전문의 진료 권장",
                    "이동 중 보온 유지 필수",
                    "강제 급식 금지"
                ),
                firstAidGuide = FirstAidGuide(
                    steps = listOf(
                        FirstAidGuideContent(
                            description = "주변을 정리하고 안전한 공간으로 옮겨주세요",
                            warning = "날개가 떨어지지 않도록, 개별 케이지 안에 두세요"
                        ),
                        FirstAidGuideContent(
                            description = "주변을 정리하고 안전한 공간으로 옮겨주세요",
                            warning = "날개가 떨어지지 않도록, 개별 케이지 안에 두세요"
                        ),
                        FirstAidGuideContent(
                            description = "주변을 정리하고 안전한 공간으로 옮겨주세요",
                            warning = "날개가 떨어지지 않도록, 개별 케이지 안에 두세요"
                        ),
                    ),
                    totalSteps = 3
                ),
                confidence = 0.88f
            )
        )
    }
}