package com.example.presentation.utils.hooks

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREAN).format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(imageFileName, ".jpg", cacheDir)
}