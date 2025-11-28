package com.example.domain.repository.feature.hospital

import com.example.domain.model.feature.hospitals.HospitalCard
import com.example.domain.usecase.feature.hospital.HospitalFilterType

interface HospitalRepository {
    suspend fun getMatchingHospitals(
        filter: HospitalFilterType,
        species: List<String>,
        lat: Double,
        lng: Double
    ): Result<List<HospitalCard>>
}