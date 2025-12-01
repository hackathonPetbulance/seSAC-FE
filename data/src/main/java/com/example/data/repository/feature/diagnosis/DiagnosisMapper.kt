package com.example.data.repository.feature.diagnosis

import com.example.data.remote.network.feature.diagnosis.model.DiagnosisData
import com.example.data.remote.network.feature.diagnosis.model.FirstAidGuideContentResponse
import com.example.data.remote.network.feature.hospital.model.HospitalDetailInfoResponse
import com.example.domain.model.feature.diagnosis.AiDiagnosis
import com.example.domain.model.feature.diagnosis.FirstAidGuide
import com.example.domain.model.feature.diagnosis.FirstAidGuideContent
import com.example.domain.model.feature.hospitals.HospitalDetail
import com.example.domain.model.feature.hospitals.HospitalLocation
import com.example.domain.model.feature.hospitals.OpenHours
import com.example.domain.model.type.EmergencyLevel

fun DiagnosisData.toDomain(): AiDiagnosis {
    return AiDiagnosis(
        animalType = this.animalType,
        emergencyLevel = when (this.emergencyLevel.uppercase()) {
            "HIGH" -> EmergencyLevel.HIGH
            "MIDDLE" -> EmergencyLevel.MIDDLE
            "LOW" -> EmergencyLevel.LOW
            else -> EmergencyLevel.MIDDLE
        },
        detectedSymptoms = this.detectedSymptoms,
        suspectedDisease = this.suspectedDisease,
        recommendedActions = this.recommendedActions,
        firstAidGuide = FirstAidGuide(
            totalSteps = this.totalSteps,
            steps = this.steps.map { it.toDomain() }
        ),
        confidence = this.confidence
    )
}

fun FirstAidGuideContentResponse.toDomain() = FirstAidGuideContent(
    description = this.description,
    warning = this.warning
)

fun HospitalDetailInfoResponse.toDomain() = HospitalDetail(
    hospitalId = this.hospitalId,
    name = this.name,
    reviewAvg = this.reviewAvg,
    reviewCount = this.reviewCount,
    openNow = this.openNow,
    todayCloseTime = this.todayCloseTime,
    distanceKm = this.distanceKm,
    phone = this.phone,
    acceptedAnimals = this.acceptedAnimals,
    location = HospitalLocation(
        address = this.location.address,
        lat = this.location.lat,
        lng = this.location.lng
    ),
    openHours = this.openHours.map { OpenHours(it.day, it.hours) }
)