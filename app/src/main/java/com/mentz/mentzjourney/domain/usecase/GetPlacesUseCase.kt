package com.mentz.mentzjourney.domain.usecase

import com.mentz.mentzjourney.data.repo.PlacesRepository
import com.mentz.mentzjourney.domain.model.PlaceModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPlacesUseCase @Inject constructor(
    private val placesRepository: PlacesRepository,
    private val placeModelMapper: PlaceModelMapper
) {
    fun invoke(searchKey: String): Flow<Result<List<PlaceModel>>> {
        return flow<Result<List<PlaceModel>>> {
            val result = placesRepository.getPlaces(searchKey = searchKey)
            val mapped = result.map {
                it.locations.map {
                    placeModelMapper.map(it)
                }
            }

            emit(mapped)
        }
    }
}