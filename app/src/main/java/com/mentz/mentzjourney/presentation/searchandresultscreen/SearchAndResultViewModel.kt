package com.mentz.mentzjourney.presentation.searchandresultscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentz.mentzjourney.domain.usecase.GetBestPlacesUseCase
import com.mentz.mentzjourney.presentation.main.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchAndResultViewModel @Inject constructor(
    private val getBestPlacesUseCase: GetBestPlacesUseCase
): ViewModel() {
    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Idle)
    val screenState = _screenState.asStateFlow()

    private val _searchKey = MutableStateFlow("")
    val searchKey = _searchKey.asStateFlow()

    init {
        _screenState.value = ScreenState.EmptySearch
    }

    fun onSearchKeyChange(value: String) = viewModelScope.launch {
        _searchKey.value = value
    }

    fun startSearch() = viewModelScope.launch {
        if (_searchKey.value.isEmpty()) {
            _screenState.value = ScreenState.EmptySearch
        } else {
            _screenState.value = ScreenState.Loading

            getBestPlacesUseCase.invoke(_searchKey.value)
                .buffer(capacity = 3)
                .collectLatest {
                    if (it.isSuccess) {
                        val list = it.getOrThrow()
                        if (list.isEmpty()) {
                            _screenState.value = ScreenState.NoResult
                        } else {
                            _screenState.value = ScreenState.Success(items = list)
                        }
                    } else {
                        _screenState.value =
                            ScreenState.Error(message = it.exceptionOrNull()?.message ?: "")
                    }
                }
        }
    }

    fun onClearSearchClicked() {
        _searchKey.value = ""
        _screenState.value = ScreenState.EmptySearch
    }
}