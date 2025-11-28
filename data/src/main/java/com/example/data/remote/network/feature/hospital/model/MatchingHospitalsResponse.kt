package com.example.data.remote.network.feature.hospital.model

import kotlinx.serialization.Serializable

@Serializable
data class MatchingHospitalsResponse(
    val hospitals: List<HospitalResponse>
)

@Serializable
data class HospitalResponse(
    val hospitalId: Long,
    val thumbnailUrl: String,
    val name: String,
    val isOpenNow: Boolean,
    val distanceKm: Double,
    val todayCloseTime: String,
    val treatableAnimals: List<String>,
    val phone: String
)