package com.example.domain.repository.feature.diagnosis

import com.example.domain.model.feature.diagnosis.AiDiagnosis

interface DiagnosisRepository {
    suspend fun requestDiagnosis(
        images: List<String>,
        animalType: String,
        symptom: String,
        onUpload: (Long, Long) -> Unit
    ): Result<AiDiagnosis>
}