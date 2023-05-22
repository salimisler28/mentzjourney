package com.mentz.mentzjourney.core

sealed class AppException(
    override val message: String?
) : RuntimeException(message) {
    object NoInternetConnectionException : AppException(message = "Connection Problem")
    data class NetworkException(
        override val message: String?
    ) : AppException(message = message)
}