package com.example.data.repository.feature.hospital

import com.example.data.remote.network.feature.hospital.HospitalApi
import com.example.data.remote.network.feature.hospital.model.MatchingHospitalsResponse
import com.example.data.utils.safeApiCall
import com.example.domain.model.feature.hospitals.HospitalCard
import com.example.domain.repository.feature.hospital.HospitalRepository
import com.example.domain.usecase.feature.hospital.HospitalFilterType
import javax.inject.Inject

class HospitalRepositoryImpl @Inject constructor(
    private val hospitalApi: HospitalApi
) : HospitalRepository {

    override suspend fun getMatchingHospitals(
        filter: HospitalFilterType,
        species: List<String>,
        lat: Double,
        lng: Double
    ): Result<List<HospitalCard>> {
        return safeApiCall<MatchingHospitalsResponse> {
            hospitalApi.getMatchingHospitals(
                filter = filter,
                species = species,
                lat = lat,
                lng = lng
            )
        }.map { it.toHospitalCardList() }
    }
}