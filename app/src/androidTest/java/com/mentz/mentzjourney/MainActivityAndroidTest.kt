package com.mentz.mentzjourney

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.mentz.mentzjourney.presentation.main.ScreenState
import com.mentz.mentzjourney.presentation.main.SearchAndResult
import org.junit.Rule
import org.junit.Test


class MainActivityAndroidTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun initTest() {
        composeRule.setContent {
            SearchAndResult(
                screenState = ScreenState.Idle,
                searchTextFieldFocusRequester = FocusRequester(),
                searchKeyValue = "",
                onSearchKeyValueChange = {},
                onSearchClicked = { },
                onClearClicked = { },
                noResultText = "",
                startSearchingText = "",
                placeHolderText = ""
            )
        }

        composeRule
            .onNodeWithTag("main_column")
            .assertIsDisplayed()
    }
}