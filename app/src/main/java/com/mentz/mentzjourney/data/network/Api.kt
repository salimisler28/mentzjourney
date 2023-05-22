package com.mentz.mentzjourney.data.network

import com.mentz.mentzjourney.data.response.GetPlacesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("XML_STOPFINDER_REQUEST")
    suspend fun getPlaces(
        @Query("language") language: String = "de",
        @Query("outputFormat") outputFormat: String = "RapidJSON",
        @Query("type_sf") type_sf: String = "any",
        @Query("name_sf") key: String,
    ): Response<GetPlacesResponse>
}