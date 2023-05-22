package com.mentz.mentzjourney.domain.usecase

import com.mentz.mentzjourney.data.dto.LocationDto
import com.mentz.mentzjourney.domain.model.PlaceModel
import javax.inject.Inject

class PlaceModelMapper @Inject constructor() {
    fun map(locationDto: LocationDto): PlaceModel {
        return PlaceModel(
            name = locationDto.name,
            type = locationDto.type,
            addInfo = locationDto.streetName ?: "",
            matchQuality = locationDto.matchQuality
        )
    }
}