package com.example.presentation.component.ui.atom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation.component.theme.PetbulanceTheme

@Composable
fun BasicButton(
    text: String,
    type: ButtonType,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonColor = when (type) {
        ButtonType.PRIMARY -> PetbulanceTheme.colorScheme.primaryButtonColor
        ButtonType.SECONDARY -> PetbulanceTheme.colorScheme.secondaryButtonColor
        ButtonType.DEFAULT -> PetbulanceTheme.colorScheme.surface
        ButtonType.EMERGENCY -> PetbulanceTheme.colorScheme.warningText
    }

    val textColor = when (type) {
        ButtonType.PRIMARY -> PetbulanceTheme.colorScheme.onPrimaryButtonColor
        ButtonType.SECONDARY -> PetbulanceTheme.colorScheme.onSecondaryButtonColor
        ButtonType.DEFAULT -> PetbulanceTheme.colorScheme.commonText
        ButtonType.EMERGENCY -> Color.White
    }

    val borderColor= when(type ) {
        ButtonType.SECONDARY -> PetbulanceTheme.colorScheme.primary
        else -> Color.Unspecified
    }

    Box(
        modifier = modifier
            .clickable { onClicked() }
            .background(buttonColor, RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(12.dp))
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier.padding(vertical = 12.dp)
        )
    }
}

enum class ButtonType {
    PRIMARY, SECONDARY, DEFAULT,EMERGENCY
}

@Preview
@Composable
private fun AppButtonPreview() {
    PetbulanceTheme {
        BasicButton(
            text = "Button",
            onClicked = {},
            type = ButtonType.PRIMARY
        )
    }
}