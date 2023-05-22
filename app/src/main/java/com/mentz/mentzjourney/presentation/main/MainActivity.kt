package com.mentz.mentzjourney.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mentz.mentzjourney.presentation.ui.theme.MentzjourneyTheme
import dagger.hilt.android.AndroidEntryPoint
import com.mentz.mentzjourney.R

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val screenState = mainViewModel.screenState.collectAsState()
            val searchKey = mainViewModel.searchKey.collectAsState()

            MentzjourneyTheme {
                SearchAndResult(
                    screenState = screenState.value,
                    searchKeyValue = searchKey.value,
                    onSearchKeyValueChange = {
                        mainViewModel.onSearchKeyChange(it)
                    }
                )
            }
        }
    }
}

@Composable
fun SearchAndResult(
    screenState: ScreenState,
    searchKeyValue: String,
    onSearchKeyValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            value = searchKeyValue,
            onValueChange = onSearchKeyValueChange
        )

        when (screenState) {
            ScreenState.Idle -> {}
            ScreenState.EmptySearch -> EmptySearch()
            ScreenState.Loading -> Loading()
            ScreenState.Success -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Loading() {
    CircularProgressIndicator()
}

@Preview(showBackground = true)
@Composable
fun EmptySearch() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = "Start Searching",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = "Search a place")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchAndResult() {
    SearchAndResult(
        screenState = ScreenState.EmptySearch,
        searchKeyValue = "",
        onSearchKeyValueChange = {}
    )
}