package com.example.domain.model.type

enum class HospitalFilterType {
    DISTANCE, TWENTY_FOUR_HOUR, IS_OPEN_NOW
}

fun HospitalFilterType.toKorean() = when(this){
    HospitalFilterType.DISTANCE -> "거리순"
    HospitalFilterType.TWENTY_FOUR_HOUR -> "24시간"
    HospitalFilterType.IS_OPEN_NOW -> "진료중"
}
