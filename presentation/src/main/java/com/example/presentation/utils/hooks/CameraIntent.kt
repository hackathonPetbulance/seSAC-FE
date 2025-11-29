package com.example.presentation.utils.hooks

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.util.Objects

/**
 * 카메라 촬영을 위한 Activity Result Launcher를 반환
 *
 * @param onResult 촬영 성공 시 결과 URI를 전달받을 call back
 * @return 카메라 촬영을 시작하는 함수
 */
@Composable
fun rememberCameraLauncher(
    onResult: (Uri) -> Unit
): () -> Unit {
    val context = LocalContext.current
    var tempUri by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                tempUri?.let(onResult)
            }
        }
    )

    return {
        val uri = createImageUri(context)
        tempUri = uri
        cameraLauncher.launch(uri)
    }
}

private fun createImageUri(context: Context): Uri {
    val file = context.createImageFile()
    return FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        "${context.packageName}.fileprovider",
        file
    )
}
