package com.mentz.mentzjourney.data.response

import com.mentz.mentzjourney.data.dto.LocationDto
import com.mentz.mentzjourney.data.dto.SystemMessageDto

data class GetPlacesResponse(
    val version: String,
    val systemMessages: List<SystemMessageDto>,
    val locations: List<LocationDto>
)