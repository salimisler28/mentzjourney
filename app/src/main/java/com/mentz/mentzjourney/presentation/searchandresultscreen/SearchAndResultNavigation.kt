package com.mentz.mentzjourney.presentation.searchandresultscreen

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

object SearchAndResultNavigation {
    const val ROUTE = "nav_search_and_result"
}

fun NavGraphBuilder.addSearchAndResultNavigation(
    noResultText: String,
    startSearchingText: String,
    placeHolderText: String,
) {
    composable(SearchAndResultNavigation.ROUTE) {
        val viewModel: SearchAndResultViewModel = hiltViewModel()

        val screenState = viewModel.screenState.collectAsState()
        val searchKey = viewModel.searchKey.collectAsState()

        SearchAndResultScreen(
            screenState = screenState.value,
            searchKeyValue = searchKey.value,
            onSearchKeyValueChange = {
                viewModel.onSearchKeyChange(it)
            },
            onSearchClicked = {
                viewModel.startSearch()
            },
            onClearClicked = {
                viewModel.onClearSearchClicked()
            },
            noResultText = noResultText,
            startSearchingText = startSearchingText,
            placeHolderText = placeHolderText
        )
    }
}