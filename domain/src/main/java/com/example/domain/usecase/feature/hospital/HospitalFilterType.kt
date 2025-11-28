package com.example.domain.usecase.feature.hospital


/**
 * 병원 필터 종류 열거형
 *
 * @property DISTANCE 거리
 * @property TWENTY_FOUR_HOUR 24시간 서비스
 * @property IS_OPEN_NOW 현재 열려있는 병원 (진료중)
 */
enum class HospitalFilterType {
    DISTANCE,
    TWENTY_FOUR_HOUR,
    IS_OPEN_NOW
}