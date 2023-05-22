package com.mentz.mentzjourney.presentation.main

import com.mentz.mentzjourney.domain.model.PlaceModel

sealed class ScreenState {
    object Idle : ScreenState()
    object EmptySearch : ScreenState()
    object Loading : ScreenState()
    data class Error(val message: String) : ScreenState()
    data class Success(val items: List<PlaceModel>) : ScreenState()
    object NoResult : ScreenState()
}
