package com.example.presentation.component.ui.atom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.presentation.component.theme.PetbulanceTheme.colorScheme
import com.example.presentation.component.theme.emp

@Composable
fun BasicSelectableChip(
    text: String,
    isSelected: Boolean,
    onClicked: () -> Unit
) {
    val backgroundColor = if (isSelected) colorScheme.primary else colorScheme.bgFrameDefault
    val textColor =
        if (isSelected) colorScheme.onPrimaryButtonColor else colorScheme.reviewTextColor
    val border = if (isSelected) colorScheme.primary else Color.LightGray

    Box(
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape((20.dp)))
            .border(1.dp, border, RoundedCornerShape(20.dp))
            .padding(8.dp)
            .clickable { onClicked() }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.emp(),
            color = textColor
        )
    }
}