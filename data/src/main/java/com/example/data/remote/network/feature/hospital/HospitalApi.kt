package com.example.data.remote.network.feature.hospital

import com.example.data.common.di.network.BASE_URL
import com.example.domain.usecase.feature.hospital.HospitalFilterType
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class HospitalApi @Inject constructor(
    private val client: HttpClient
) {
    private val baseUrl = "${BASE_URL}/hospitals/matching"

    suspend fun getMatchingHospitals(
        filter: HospitalFilterType,
        species: List<String>,
        lat: Double,
        lng: Double
    ): HttpResponse {
        return client.get(baseUrl) {
            contentType(ContentType.Application.Json)
            parameter("filter", filter.name)
            species.forEach { parameter("species", it) }
            parameter("lat", lat)
            parameter("lng", lng)
        }
    }
}