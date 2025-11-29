package com.example.presentation.screen.diagnosis

import android.Manifest
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
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
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Upload
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.presentation.component.theme.PetbulanceTheme
import com.example.presentation.component.theme.PetbulanceTheme.colorScheme
import com.example.presentation.component.theme.emp
import com.example.presentation.component.ui.CommonPadding
import com.example.presentation.component.ui.atom.BasicButton
import com.example.presentation.component.ui.atom.BasicButtonWithIcon
import com.example.presentation.component.ui.atom.BasicIcon
import com.example.presentation.component.ui.atom.BasicInputTextField
import com.example.presentation.component.ui.atom.ButtonType
import com.example.presentation.component.ui.atom.IconResource
import com.example.presentation.component.ui.organism.AppTopBar
import com.example.presentation.component.ui.organism.TopBarAlignment
import com.example.presentation.component.ui.organism.TopBarInfo
import com.example.presentation.utils.PermissionHandler
import com.example.presentation.utils.error.ErrorDialog
import com.example.presentation.utils.error.ErrorDialogState
import com.example.presentation.utils.hooks.rememberCameraLauncher
import com.example.presentation.utils.hooks.rememberPhotoPickerLauncher
import com.example.presentation.utils.nav.ScreenDestinations
import com.example.presentation.utils.nav.safeNavigate
import kotlinx.coroutines.flow.MutableSharedFlow

@RequiresExtension(extension = Build.VERSION_CODES.R, version = 2)
@Composable
fun DiagnosisScreen(
    navController: NavController,
    argument: DiagnosisArgument,
    data: DiagnosisData
) {
    var errorDialogState by remember { mutableStateOf(ErrorDialogState.idle()) }

    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var launchCamera by remember { mutableStateOf(false) }
    var hasCameraPermission by remember { mutableStateOf(false) }

    val cameraLauncher = rememberCameraLauncher { uri ->
        if (imageUris.size < 3) {
            imageUris = imageUris + uri
        }
        argument.intent(DiagnosisIntent.UpdateImageUris(imageUris))
    }

    val galleryLauncher = rememberPhotoPickerLauncher(multiple = true, maxItems = 3) { uris ->
        imageUris = (imageUris + uris).take(3)
        argument.intent(DiagnosisIntent.UpdateImageUris(imageUris))
    }

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
        },
        containerColor = colorScheme.bgFrameDefault
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            DiagnosisScreenContents(
                imageUris = data.imageUris,
                animalSpecies = data.animalSpecies,
                description = data.description,
                onAnimalSpeciesNameChanged = { argument.intent(DiagnosisIntent.UpdateAnimalSpecies(it)) },
                onDescriptionChanged = { argument.intent(DiagnosisIntent.UpdateDescription(it)) },
                onCameraOpenButtonClicked = { launchCamera = true },
                onGalleryOpenClicked = { galleryLauncher() },
                onRequestDiagnosisButtonClicked = {
                    argument.intent(
                        DiagnosisIntent.RequestDiagnosis(
                            onUpload = { _, _ -> } /* TODO : Progress */
                        )
                    )
                }
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

    if (launchCamera) {
        PermissionHandler(
            permission = Manifest.permission.CAMERA,
            onPermissionGranted = {
                hasCameraPermission = true
                cameraLauncher()
                launchCamera = false
            },
            onPermissionDenied = {
                hasCameraPermission = false
                Log.d("siria22", "카메라 권한이 거부되었습니다.")
                launchCamera = false
            }
        )
    }
}

@Composable
private fun DiagnosisScreenContents(
    imageUris: List<Uri>,
    animalSpecies: String,
    description: String,
    onAnimalSpeciesNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onCameraOpenButtonClicked: () -> Unit,
    onGalleryOpenClicked: () -> Unit,
    onRequestDiagnosisButtonClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(CommonPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        GuideCard()

        /* TODO : 업로드 된 이미지 보여줄 방법 */
        UploadCard(imageUris, onCameraOpenButtonClicked, onGalleryOpenClicked)

        UserTextInputSection(
            animalSpecies = animalSpecies,
            description = description,
            onAnimalSpeciesNameChanged = onAnimalSpeciesNameChanged,
            onDescriptionChanged = onDescriptionChanged
        )

        BasicButton(
            text = "증상 분석하기",
            type = ButtonType.PRIMARY,
            onClicked = onRequestDiagnosisButtonClicked
        )
    }
}

@Composable
private fun GuideCard() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .background(
                color = colorScheme.primary.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                1.dp, colorScheme.primary.copy(0.2f),
                RoundedCornerShape(16.dp)
            )
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

@Composable
private fun UploadCard(
    imageUris: List<Uri>,
    onCameraOpenButtonClicked: () -> Unit,
    onGalleryOpenClicked: () -> Unit
) {
    if (imageUris.isNullOrEmpty()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .drawBehind {
                    val stroke = Stroke(
                        width = 1.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )
                    drawRoundRect(
                        color = Color.Black,
                        style = stroke,
                        cornerRadius = CornerRadius(16.dp.toPx())
                    )
                }
                .padding(vertical = 24.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFF6F6F6),
                        shape = RoundedCornerShape(32.dp)
                    )
                    .padding(12.dp)
            ) {
                BasicIcon(
                    iconResource = IconResource.Vector(Icons.Outlined.Upload),
                    contentDescription = "Upload Pictures",
                    size = 40.dp,
                    tint = Color(0xFF393939)
                )
            }

            Text(
                text = "사진 업로드",
                color = Color(0xFF000000),
                style = MaterialTheme.typography.titleSmall.emp(),
            )

            Text(
                text = "증상이 보이는 사진을 선택하거나\n" +
                        "카메라로 직접 촬영하세요",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF5A5A5A)
            )
            BasicButtonWithIcon(
                modifier = Modifier.fillMaxWidth(0.6f),
                text = "카메라로 촬영",
                onClicked = onCameraOpenButtonClicked,
                type = ButtonType.PRIMARY,
                iconResource = IconResource.Vector(Icons.Outlined.CameraAlt),
            )
            BasicButtonWithIcon(
                modifier = Modifier.fillMaxWidth(0.6f),
                text = "갤러리에서 선택",
                onClicked = onGalleryOpenClicked,
                type = ButtonType.DEFAULT,
                iconResource = IconResource.Vector(Icons.Outlined.Upload)
            )
        }
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .drawBehind {
                    val stroke = Stroke(
                        width = 1.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )
                    drawRoundRect(
                        color = Color.Black,
                        style = stroke,
                        cornerRadius = CornerRadius(16.dp.toPx())
                    )
                }
                .padding(vertical = 24.dp)
                .fillMaxWidth()
        ) {

        }
    }
}

@Composable
private fun UserTextInputSection(
    animalSpecies: String,
    description: String,
    onAnimalSpeciesNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = "직접 입력하기",
            color = colorScheme.textPrimary,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
        )

        Text(
            text = "동물종",
            color = colorScheme.textPrimary,
            style = MaterialTheme.typography.bodyLarge,
        )

        BasicInputTextField(
            value = animalSpecies,
            onValueChange = { onAnimalSpeciesNameChanged(it) },
            placeholder = "포유류, 파충류 등",
            singleLine = true
        )

        Text(
            text = "주요 증상",
            color = colorScheme.textPrimary,
            style = MaterialTheme.typography.bodyLarge,
        )

        BasicInputTextField(
            value = description,
            onValueChange = { onDescriptionChanged(it) },
            placeholder = "예: 식욕 없음, 활동량 감소, 배변 이상 등",
            singleLine = false,
            sizeFactor = 4
        )
    }
}


@Preview(apiLevel = 34)
@Composable
private fun UploadCardPreview() {
    PetbulanceTheme {
        UploadCard(emptyList(), {}, {})
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.R, version = 2)
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
            data = DiagnosisData.empty()
        )
    }
}