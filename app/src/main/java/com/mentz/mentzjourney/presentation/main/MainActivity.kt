package com.mentz.mentzjourney.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mentz.mentzjourney.presentation.ui.theme.MentzjourneyTheme
import dagger.hilt.android.AndroidEntryPoint
import com.mentz.mentzjourney.R
import com.mentz.mentzjourney.domain.model.PlaceModel
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val screenState = mainViewModel.screenState.collectAsState()
            val searchKey = mainViewModel.searchKey.collectAsState()

            val focusManager = LocalFocusManager.current
            val focusRequester = remember { FocusRequester() }

            LaunchedEffect(key1 = Unit, block = {
                delay(200)
                focusRequester.requestFocus()
            })

            MentzjourneyTheme {
                SearchAndResult(
                    screenState = screenState.value,
                    searchTextFieldFocusRequester = focusRequester,
                    searchKeyValue = searchKey.value,
                    onSearchKeyValueChange = {
                        mainViewModel.onSearchKeyChange(it)
                    },
                    onSearchClicked = {
                        mainViewModel.startSearch()
                        focusManager.clearFocus()
                    },
                    onClearClicked = {
                        mainViewModel.onClearSearchClicked()
                        focusRequester.requestFocus()
                    }
                )
            }
        }
    }
}

@Composable
fun SearchAndResult(
    screenState: ScreenState,
    searchTextFieldFocusRequester: FocusRequester,
    searchKeyValue: String,
    onSearchKeyValueChange: (String) -> Unit,
    onSearchClicked: () -> Unit,
    onClearClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            focusRequester = searchTextFieldFocusRequester,
            value = searchKeyValue,
            onValueChange = onSearchKeyValueChange,
            onSearchClicked = onSearchClicked,
            onClearClicked = onClearClicked
        )

        when (screenState) {
            is ScreenState.Idle -> {}
            is ScreenState.EmptySearch -> EmptySearch()
            is ScreenState.Loading -> Loading()
            is ScreenState.Error -> {}
            is ScreenState.NoResult -> {
                NoResult()
            }

            is ScreenState.Success -> {
                Results(items = screenState.items)
            }
        }
    }
}

@Composable
fun NoResult() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = "No result found",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Loading() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun EmptySearch() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(16.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = "Start Searching",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
fun Results(
    items: List<PlaceModel>
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items.forEach {
                item { PlaceItem(it) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceItem(placeModel: PlaceModel) {
    Card(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column {
            Text(text = placeModel.name)
            Text(text = placeModel.type)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    focusRequester: FocusRequester,
    value: String,
    onValueChange: (String) -> Unit,
    onSearchClicked: () -> Unit,
    onClearClicked: () -> Unit,
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
            .focusRequester(focusRequester),
        singleLine = true,
        maxLines = 1,
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClicked.invoke()
            }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        trailingIcon = {
            AnimatedVisibility(
                visible = value != "",
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(
                    onClick = {
                        onClearClicked.invoke()
                    }
                ) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchAndResult() {
    SearchAndResult(
        screenState = ScreenState.EmptySearch,
        searchKeyValue = "",
        onSearchKeyValueChange = {},
        onSearchClicked = {},
        searchTextFieldFocusRequester = FocusRequester(),
        onClearClicked = {}
    )
}