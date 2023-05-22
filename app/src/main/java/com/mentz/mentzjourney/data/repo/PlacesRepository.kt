package com.mentz.mentzjourney.data.repo

import com.mentz.mentzjourney.data.response.GetPlacesResponse

interface PlacesRepository {
    suspend fun getPlaces(searchKey: String): Result<GetPlacesResponse>
}