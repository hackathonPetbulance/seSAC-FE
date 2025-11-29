package com.example.data.repository.feature.hospital

import com.example.domain.model.feature.hospitals.HospitalCard
import com.example.domain.repository.feature.hospital.HospitalRepository
import com.example.domain.usecase.feature.hospital.HospitalFilterType
import javax.inject.Inject

class MockHospitalRepository @Inject constructor() : HospitalRepository {

    override suspend fun getMatchingHospitals(
        filter: HospitalFilterType,
        species: String,
        lat: Double,
        lng: Double
    ): Result<List<HospitalCard>> {
        return Result.success(
            listOf(
                HospitalCard.stub(),
                HospitalCard.stub().copy(
                    hospitalId = 10L,
                    name = "병원1",
                    isOpenNow = false,
                    distanceKm = 1.0,
                    treatableAnimals = listOf("토끼"),
                    phone = "02-124-5678",
                    thumbnailUrl = "fffssss",
                    todayCloseTime = "21:00"
                )
            )
        )
    }
}