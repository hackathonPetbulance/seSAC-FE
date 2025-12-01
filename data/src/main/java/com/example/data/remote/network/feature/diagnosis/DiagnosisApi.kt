package com.example.data.remote.network.feature.diagnosis

import android.content.Context
import android.net.Uri
import com.example.data.common.di.network.BASE_URL
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.HttpClient
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.append
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.utils.io.streams.asInput
import javax.inject.Inject

class DiagnosisApi @Inject constructor(
    private val client: HttpClient,
    @param:ApplicationContext private val context: Context
) {
    private val baseUrl = "${BASE_URL}/ai"

    suspend fun requestDiagnosis(
        images: List<Uri>,
        animalType: String,
        symptom: String,
        onUpload: (bytesSent: Long, totalBytes: Long) -> Unit
    ): HttpResponse {
        return client.post("$baseUrl/diagnosis") {
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("animalType", animalType)
                        append("symptom", symptom)

                        images.filterNotNull().forEachIndexed { index, uri ->
                            val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
                            val ext = if (mimeType.contains("png")) "png" else "jpg"
                            val fileName = "image_$index.$ext"

                            append(
                                key = "images", // Todo : 서버가 리스트를 받을 때 사용하는 키 이름 (images[] 인지 images 인지 백엔드 확인 필요)
                                filename = fileName,
                                contentType = ContentType.parse(mimeType)
                            ) {
                                context.contentResolver.openInputStream(uri)?.asInput()
                                    ?: throw IllegalStateException("이미지를 열 수 없습니다: $uri")
                            }
                        }
                    }
                )
            )

            onUpload { bytesSent, totalBytes ->
                onUpload(bytesSent, totalBytes ?: -1L) // Presentation layer에서 -1L을 받으면 오류 처리
            }
        }
    }
}
