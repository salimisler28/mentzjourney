package com.mentz.mentzjourney

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import com.mentz.mentzjourney.presentation.TestTags
import com.mentz.mentzjourney.presentation.main.MainActivity
import org.junit.Rule
import org.junit.Test

class MainScreenEndToEndTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun screen_is_visible() {
        composeRule
            .onNodeWithTag(TestTags.mainColumn)
            .assertIsDisplayed()
    }

    @Test
    fun screen_state_is_loading_after_search_clicked() {
        composeRule
            .onNodeWithTag(TestTags.searchBar)
            .performTextInput("de")

        composeRule
            .onNodeWithTag(TestTags.searchBar)
            .performImeAction()

        composeRule
            .onNodeWithTag(TestTags.loadingProgress)
            .assertIsDisplayed()
    }
}