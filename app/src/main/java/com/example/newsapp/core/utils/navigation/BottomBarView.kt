package com.example.newsapp.core.utils.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.uikit.R
import com.example.newsapp.features.settings.navigation.SettingsRoute
import com.example.newsapp.features.home.navigation.HomeRoute
import com.example.uikit.utills.Drawables

sealed class BottomBarView(
    val route: String,
    @StringRes val nameRes: Int,
    @DrawableRes val iconRes: Int
) {
    object Main : BottomBarView(
        route = HomeRoute.endpoint(),
        nameRes = R.string.home_screen_title,
        iconRes = Drawables.ic_news
    )

    object Settings : BottomBarView(
        route = SettingsRoute.endpoint(),
        nameRes = R.string.settings_screen_title,
        iconRes = Drawables.ic_settings
    )
}
