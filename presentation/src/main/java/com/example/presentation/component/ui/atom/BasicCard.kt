package com.example.presentation.component.ui.atom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.presentation.component.theme.PetbulanceTheme.colorScheme
import com.example.presentation.component.ui.CommonPadding


@Composable
fun BasicCard(
    modifier: Modifier = Modifier,
    cardPaddingValue: Dp = CommonPadding,
    contentPaddingValue: Dp = 12.dp,
    backgroundColor: Color = colorScheme.bgFrameDefault,
    borderColor: Color = Color.LightGray,
    content: @Composable () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(contentPaddingValue),
        modifier = modifier
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .padding(cardPaddingValue)
    ) {
        content()
    }
}