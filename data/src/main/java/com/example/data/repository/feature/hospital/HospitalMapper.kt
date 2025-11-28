package com.example.data.repository.feature.hospital

import com.example.data.remote.network.feature.hospital.model.HospitalResponse
import com.example.data.remote.network.feature.hospital.model.MatchingHospitalsResponse
import com.example.domain.model.feature.hospitals.HospitalCard

fun MatchingHospitalsResponse.toHospitalCardList(): List<HospitalCard> {
    return this.hospitals.map { it.toDomain() }
}

fun HospitalResponse.toDomain(): HospitalCard {
    return HospitalCard(
        hospitalId = this.hospitalId,
        thumbnailUrl = this.thumbnailUrl,
        name = this.name,
        isOpenNow = this.isOpenNow,
        distanceKm = this.distanceKm,
        todayCloseTime = this.todayCloseTime,
        treatableAnimals = this.treatableAnimals,
        phone = this.phone
    )
}