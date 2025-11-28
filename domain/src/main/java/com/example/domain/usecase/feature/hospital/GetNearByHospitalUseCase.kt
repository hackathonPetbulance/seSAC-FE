package com.example.domain.usecase.feature.hospital

import com.example.domain.model.feature.hospitals.HospitalCard
import com.example.domain.repository.feature.hospital.HospitalRepository
import javax.inject.Inject

class GetNearByHospitalUseCase @Inject constructor(
    private val repository: HospitalRepository
) {
    suspend operator fun invoke(): Result<List<HospitalCard>> {
        return repository.getMatchingHospitals(
            filter = HospitalFilterType.DISTANCE,
            species = emptyList(),
            lat = 0.0,
            lng = 0.0
        )
    }
}