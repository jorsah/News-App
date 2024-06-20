package com.example.newsapp.features.home.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.newsapp.features.home.HomeScreen
import com.example.navigation.NavigationConfiguration
import com.example.navigation.NavigationDestination
import com.example.navigation.NavigationRoute

object HomeRoute : NavigationRoute.ScreenRoute() {

    override fun endpoint(): String = "home"

    @Composable
    override fun View(navController: NavController) {
        HomeScreen(navController)
    }

    sealed class Entry : NavigationDestination {
        object Single : Entry() {
            override fun getRoute(): NavigationRoute = HomeRoute
            override fun getConfiguration(): NavigationConfiguration = NavigationConfiguration(
                popUpTo = HomeRoute,
                inclusive = true
            )
        }
    }
}
