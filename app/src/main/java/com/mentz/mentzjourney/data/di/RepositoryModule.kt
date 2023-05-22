package com.mentz.mentzjourney.data.di

import com.mentz.mentzjourney.data.repo.PlacesRepository
import com.mentz.mentzjourney.data.repo.PlacesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Provides
    abstract fun bindPlacesRepo(placesRepositoryImpl: PlacesRepositoryImpl): PlacesRepository
}