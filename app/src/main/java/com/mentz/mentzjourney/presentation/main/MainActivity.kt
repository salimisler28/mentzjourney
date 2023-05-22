package com.mentz.mentzjourney.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mentz.mentzjourney.R
import com.mentz.mentzjourney.presentation.searchandresultscreen.SearchAndResultNavigation
import com.mentz.mentzjourney.presentation.searchandresultscreen.addSearchAndResultNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navHostController = rememberNavController()

            NavHost(
                navController = navHostController,
                startDestination = SearchAndResultNavigation.ROUTE
            ) {
                addSearchAndResultNavigation(
                    noResultText = getString(R.string.no_result),
                    startSearchingText = getString(R.string.start_searching),
                    placeHolderText = getString(R.string.search_a_place)
                )
            }
        }
    }
}