package com.example.data.utils

import android.util.Log
import com.example.domain.exception.BadRequestException
import com.example.domain.exception.InternalServerErrorException
import com.example.domain.exception.InvalidCredentialsException
import com.example.domain.exception.NotFoundException
import com.example.domain.exception.UnknownNetworkException
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val status: Int,
    val success: Boolean,
    val data: T? = null,
    val message: String? = null
)

suspend inline fun <reified T> safeApiCall(apiCall: suspend () -> HttpResponse): Result<T> {
    return try {
        val response = apiCall()
        val responseBody = response.body<BaseResponse<T>>()

        if (response.status.value in 200..299) {
            responseBody.data?.let { Result.success(it) }
                ?: Result.failure(Exception("Data is null"))
        } else {
            Result.failure(
                mapToDomainException(
                    response.status.value,
                    responseBody.message ?: "No information from server"
                )
            )
        }
    } catch (e: Exception) {
        val exception = when (e) {
            is ClientRequestException -> parseErrorResponse(e.response)
            is ServerResponseException -> parseErrorResponse(e.response)
            else -> e
        }
        Result.failure(exception)
    }
}

suspend fun parseErrorResponse(response: HttpResponse): Exception {
    val errorBody = try {
        response.body<BaseResponse<String?>>()
    } catch (e: Exception) {
        null
    }
    val message = errorBody?.message ?: "An error occurred"
    Log.e("siria22", "ApiErrorResponse: ${response.status.value} - $message")

    return mapToDomainException(response.status.value, message)
}

fun mapToDomainException(code: Int, message: String): Exception {
    return when (code) {
        400 -> BadRequestException(message)
        401 -> InvalidCredentialsException(message)
        404 -> NotFoundException(message)
        500 -> InternalServerErrorException(message)
        else -> if (code in 400..499) {
            com.example.domain.exception.ClientException(code, message)
        } else if (code in 500..599) {
            com.example.domain.exception.ServerException(code, message)
        } else {
            UnknownNetworkException("Unknown error with code $code: $message")
        }
    }
}