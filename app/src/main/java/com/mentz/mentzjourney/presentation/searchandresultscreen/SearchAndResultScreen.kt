package com.mentz.mentzjourney.presentation.searchandresultscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mentz.mentzjourney.R
import com.mentz.mentzjourney.domain.model.PlaceModel
import com.mentz.mentzjourney.presentation.TestTags
import kotlinx.coroutines.delay

@Composable
fun SearchAndResultScreen(
    screenState: ScreenState,
    searchKeyValue: String,
    onSearchKeyValueChange: (String) -> Unit,
    onSearchClicked: () -> Unit,
    onClearClicked: () -> Unit,
    noResultText: String,
    startSearchingText: String,
    placeHolderText: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag(TestTags.mainColumn)
    ) {
        SearchBar(
            value = searchKeyValue,
            onValueChange = onSearchKeyValueChange,
            onSearchClicked = onSearchClicked,
            onClearClicked = onClearClicked,
            placeHolderText = placeHolderText
        )

        when (screenState) {
            is ScreenState.Idle -> {}
            is ScreenState.EmptySearch -> EmptySearch(text = startSearchingText)
            is ScreenState.Loading -> Loading()
            is ScreenState.Error -> Errors(message = screenState.message)
            is ScreenState.NoResult -> NoResult(text = noResultText)
            is ScreenState.Success -> Results(items = screenState.items)
        }
    }
}

@Composable
fun Errors(message: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(16.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = null
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun NoResult(
    text: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = text,
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
        CircularProgressIndicator(
            modifier = Modifier
                .testTag(TestTags.loadingProgress)
        )
    }
}

@Composable
fun EmptySearch(
    text: String
) {
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
            text = text,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
fun Results(
    items: List<PlaceModel>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("column_results")
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

@Composable
fun PlaceItem(placeModel: PlaceModel) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        val (leadingIcon, name, type, quality) = createRefs()

        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            modifier = Modifier
                .constrainAs(leadingIcon) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
        )

        Text(
            text = placeModel.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .constrainAs(name) {
                    start.linkTo(leadingIcon.end, margin = 8.dp)
                    top.linkTo(parent.top)
                    end.linkTo(quality.start, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
        )

        Text(
            text = placeModel.type,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .constrainAs(type) {
                    start.linkTo(leadingIcon.end, margin = 8.dp)
                    top.linkTo(name.bottom)
                },
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
        )

        Text(
            text = placeModel.matchQuality.toString(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .constrainAs(quality) {
                    end.linkTo(parent.end, margin = 8.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSearchClicked: () -> Unit,
    onClearClicked: () -> Unit,
    placeHolderText: String
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = FocusRequester()

    LaunchedEffect(key1 = Unit, block = {
        delay(100)
        focusRequester.requestFocus()
    })

    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = placeHolderText)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .focusRequester(focusRequester)
            .testTag(TestTags.searchBar),
        singleLine = true,
        maxLines = 1,
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClicked.invoke()
                focusManager.clearFocus()
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
                        focusRequester.requestFocus()
                    }
                ) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                }
            }
        }
    )
}