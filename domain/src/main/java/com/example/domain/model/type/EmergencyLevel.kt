package com.example.domain.model.type

enum class EmergencyLevel {
    HIGH,
    MIDDLE,
    LOW
}

fun EmergencyLevel.toKorean(): String {
    return when (this) {
        EmergencyLevel.HIGH -> "높음"
        EmergencyLevel.MIDDLE -> "중간"
        EmergencyLevel.LOW -> "낮음"
    }
}