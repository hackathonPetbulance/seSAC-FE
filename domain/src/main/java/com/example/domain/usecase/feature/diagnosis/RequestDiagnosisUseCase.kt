package com.example.domain.usecase.feature.diagnosis

import com.example.domain.model.feature.diagnosis.AiDiagnosis
import com.example.domain.repository.feature.diagnosis.DiagnosisRepository
import javax.inject.Inject

class RequestDiagnosisUseCase @Inject constructor(
    private val repository: DiagnosisRepository
) {
    suspend operator fun invoke(
        images: List<String>,
        animalType: String,
        symptom: String,
        onUpload: (bytesSent: Long, totalBytes: Long) -> Unit
    ): AiDiagnosis {
        return repository.requestDiagnosis(
            images = images,
            animalType = animalType,
            symptom = symptom,
            onUpload = onUpload
        ).getOrThrow()
    }
}
