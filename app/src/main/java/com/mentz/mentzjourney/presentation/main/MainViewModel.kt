package com.mentz.mentzjourney.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {
    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Idle)
    val screenState = _screenState.asStateFlow()

    private val _searchKey = MutableStateFlow("")
    val searchKey = _searchKey.asStateFlow()

    init {
        _screenState.value = ScreenState.EmptySearch
    }

    fun onSearchKeyChange(value: String) = viewModelScope.launch {
        _searchKey.value = value

        if (value.isEmpty()) {
            _screenState.value = ScreenState.EmptySearch
        } else {
            _screenState.value = ScreenState.Loading
            delay(1000)
            _screenState.value = ScreenState.Success
        }
    }
}