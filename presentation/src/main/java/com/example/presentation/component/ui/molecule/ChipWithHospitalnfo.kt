package com.example.presentation.component.ui.molecule

import androidx.compose.runtime.Composable
import com.example.presentation.component.theme.PetbulanceTheme.colorScheme
import com.example.presentation.component.ui.atom.BasicChip

@Composable
fun ChipWithHospitalInfo(info: String) {

    val chipBackgroundColor = when (info) {
        "야간진료" -> colorScheme.warningText
        "응급실" -> colorScheme.warningText
        else -> colorScheme.surface
    }
    BasicChip(
        text = info,
        color = chipBackgroundColor
    )
}