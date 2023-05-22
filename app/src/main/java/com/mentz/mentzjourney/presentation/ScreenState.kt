package com.mentz.mentzjourney.presentation

sealed class ScreenState {
    object Idle: ScreenState()
    object EmptySearch: ScreenState()
    object Loading: ScreenState()
    object Success: ScreenState()
}
