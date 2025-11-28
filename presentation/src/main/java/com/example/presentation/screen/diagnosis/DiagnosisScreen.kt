package com.example.presentation.screen.diagnosis

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.presentation.component.theme.PetbulanceTheme
import com.example.presentation.component.theme.PetbulanceTheme.colorScheme
import com.example.presentation.component.theme.emp
import com.example.presentation.component.ui.CommonPadding
import com.example.presentation.component.ui.atom.BasicIcon
import com.example.presentation.component.ui.atom.IconResource
import com.example.presentation.component.ui.organism.AppTopBar
import com.example.presentation.component.ui.organism.TopBarAlignment
import com.example.presentation.component.ui.organism.TopBarInfo
import com.example.presentation.utils.error.ErrorDialog
import com.example.presentation.utils.error.ErrorDialogState
import com.example.presentation.utils.nav.ScreenDestinations
import com.example.presentation.utils.nav.safeNavigate
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
fun DiagnosisScreen(
    navController: NavController,
    argument: DiagnosisArgument,
    data: DiagnosisData
) {
    var errorDialogState by remember { mutableStateOf(ErrorDialogState.idle()) }

    LaunchedEffect(argument.event) {
        argument.event.collect { event ->
            when (event) {
                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                topBarInfo = TopBarInfo(
                    text = "펫뷸런스 AI",
                    textAlignment = TopBarAlignment.START,
                    isLeadingIconAvailable = false,
                    onLeadingIconClicked = {},
                    leadingIconResource = IconResource.Vector(Icons.AutoMirrored.Filled.KeyboardArrowLeft),
                ),
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            DiagnosisScreenContents(

            )
        }
    }

    if (errorDialogState.isErrorDialogVisible) {
        ErrorDialog(
            errorDialogState = errorDialogState,
            errorHandler = {
                errorDialogState.toggleVisibility()
                navController.safeNavigate(ScreenDestinations.Home.route)
            }
        )
    }

    // BackHandler { }
}

@Composable
private fun DiagnosisScreenContents(

) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(CommonPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .background(
                    color = colorScheme.primary.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(16.dp)
                )
                .border(1.dp, colorScheme.primary, RoundedCornerShape(16.dp))
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicIcon(
                    iconResource = IconResource.Vector(Icons.Default.Info),
                    contentDescription = "Informations",
                    size = 20.dp,
                    tint = colorScheme.primary
                )
                Text(
                    text = "증상 사진 촬영 가이드",
                    style = MaterialTheme.typography.titleSmall.emp(),
                )
            }
            Text(
                text = "• 밝은 곳에서 반려동물의 전체 모습을 촬영해주세요",
                style = MaterialTheme.typography.bodySmall.emp(),
                color = Color(0xFF5A5A5A)
            )
            Text(
                text = "• 증상이 있는 부위를 명확하게 포착해주세요",
                style = MaterialTheme.typography.bodySmall.emp(),
                color = Color(0xFF5A5A5A)
            )
            Text(
                text = "• 여러 각도에서 촬영하면 더 정확한 분석이 가능합니다",
                style = MaterialTheme.typography.bodySmall.emp(),
                color = Color(0xFF5A5A5A)
            )
        }
    }
}


@Preview(apiLevel = 34)
@Composable
private fun DiagnosisScreenPreview() {
    PetbulanceTheme {
        DiagnosisScreen(
            navController = rememberNavController(),
            argument = DiagnosisArgument(
                intent = { },
                state = DiagnosisState.Init,
                event = MutableSharedFlow()
            ),
            data = DiagnosisData(
                data = ""
            )
        )
    }
}