package com.mentz.mentzjourney.data.repo

import com.mentz.mentzjourney.data.datasource.ApiDataSource
import com.mentz.mentzjourney.data.response.GetPlacesResponse
import javax.inject.Inject

class PlacesRepositoryImpl @Inject constructor(
    private val apiDataSource: ApiDataSource
) : PlacesRepository {
    override suspend fun getPlaces(searchKey: String): Result<GetPlacesResponse> {
        return apiDataSource.getPlaces(key = searchKey)
    }
}