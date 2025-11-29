package com.example.presentation.screen.diagnosis

import android.net.Uri

data class DiagnosisData(
    val imageUris: List<Uri?>,
    val animalSpecies: String,
    val description: String
) {
    companion object {
        fun empty() = DiagnosisData(
            imageUris = listOf(null, null, null),
            animalSpecies = "",
            description = ""
        )
    }
}