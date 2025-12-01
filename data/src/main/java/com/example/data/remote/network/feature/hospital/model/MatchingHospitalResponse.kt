package com.example.data.remote.network.feature.hospital.model

import kotlinx.serialization.Serializable

@Serializable
data class MatchingHospitalResponse(
    val hospitalId: Long,
    val thumbnailUrl: String,
    val name: String,
    val openNow: Boolean,
    val distanceKm: Double,
    val todayCloseTime: String,
    val treatableAnimals: List<String>,
    val phone: String
)