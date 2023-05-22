package com.mentz.mentzjourney.data.dto

data class SystemMessageDto(
    val type: String,
    val module: String,
    val code: Int,
    val text: String
)
