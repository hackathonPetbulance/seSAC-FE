package com.example.data.repository.feature.hospital

import com.example.data.remote.network.feature.hospital.model.MatchingHospitalResponse
import com.example.domain.model.feature.hospitals.MatchedHospital

fun MatchingHospitalResponse.toDomain(): MatchedHospital {
    return MatchedHospital(
        hospitalId = this.hospitalId,
        thumbnailUrl = this.thumbnailUrl,
        name = this.name,
        isOpenNow = this.openNow,
        distanceKm = this.distanceKm,
        todayCloseTime = this.todayCloseTime,
        treatableAnimals = this.treatableAnimals,
        phone = this.phone
    )
}