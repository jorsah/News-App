package com.example.newsapp.features.settings.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.newsapp.features.settings.SettingsScreen
import com.example.navigation.NavigationRoute

object SettingsRoute : NavigationRoute.ScreenRoute() {

    override fun endpoint(): String = "settings"

    @Composable
    override fun View(navController: NavController) {
        SettingsScreen(navController)
    }
}
