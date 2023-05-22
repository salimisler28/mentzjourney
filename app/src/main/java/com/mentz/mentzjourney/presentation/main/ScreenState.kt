package com.mentz.mentzjourney.presentation.main

sealed class ScreenState {
    object Idle: ScreenState()
    object EmptySearch: ScreenState()
    object Loading: ScreenState()
    object Success: ScreenState()
}
