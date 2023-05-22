package com.mentz.mentzjourney.data.datasource

import com.mentz.mentzjourney.data.helpers.makeRequest
import com.mentz.mentzjourney.data.network.Api
import com.mentz.mentzjourney.data.response.GetPlacesResponse
import javax.inject.Inject

class ApiDataSource @Inject constructor(
    private val api: Api
) {
    suspend fun getPlaces(key: String): Result<GetPlacesResponse> {
        return makeRequest { api.getPlaces(key = key) }
    }
}