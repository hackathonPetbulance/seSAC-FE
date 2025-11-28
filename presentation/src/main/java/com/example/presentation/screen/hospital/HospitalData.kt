package com.example.presentation.screen.hospital

data class HospitalData(
    val data: String
) {
    companion object {
        fun empty() = HospitalData(
            data = ""
        )
    }
}