package com.example.domain.model.feature.hospitals

data class HospitalCard(
    val hospitalId: Long,
    val thumbnailUrl: String,
    val name: String,
    val isOpenNow: Boolean,
    val distanceKm: Double,
    val todayCloseTime: String,
    val treatableAnimals: List<String>,
    val phone: String,
) {
    companion object {
        fun stub() = HospitalCard(
            hospitalId = 12L,
            name = "서울 특수동물 의료센터",
            isOpenNow = true,
            distanceKm = 1.2,
            treatableAnimals = listOf("토끼", "설치류"),
            phone = "02-123-4567",
            thumbnailUrl = "sfadsfdsafadsfdasf",
            todayCloseTime = "20:00"
        )
    }
}
