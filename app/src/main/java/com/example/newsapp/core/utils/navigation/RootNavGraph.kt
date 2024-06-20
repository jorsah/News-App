package com.example.newsapp.core.utils.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.newsapp.features.article.navigation.ArticleRoute
import com.example.newsapp.features.home.navigation.HomeRoute
import com.example.newsapp.features.settings.navigation.SettingsRoute
import com.example.navigation.activateNavigationRoutes
import com.example.uikit.style.Colors
import com.example.uikit.style.TextStyle
import com.example.uikit.utills.SpacerHeight
import com.example.uikit.utills.SpacerWidth

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RootNavGraph(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) {
        NavHost(
            navController = navController,
            startDestination = HomeRoute.endpoint()
        ) {
            activateNavigationRoutes(
                navController,
                listOf(
                    HomeRoute,
                    ArticleRoute,
                    SettingsRoute
                )
            )
        }
    }
}


@Composable
private fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarView.Main,
        BottomBarView.Settings
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination =
        screens.any {
            it.route == currentDestination?.route
        }

    if (bottomBarDestination) {
        Column(
            Modifier
                .navigationBarsPadding()
                .fillMaxWidth()
        ) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth(),
                color = Colors.outlineMain,
                thickness = 1.dp
            )
            BottomNavigation(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp),
                backgroundColor = Color.White,
                elevation = 0.dp
            ) {
                SpacerWidth(width = 8.dp)

                screens.forEach { screen ->
                    AddItem(
                        view = screen,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                }

                SpacerWidth(width = 8.dp)
            }
        }
    }
}

@Composable
private fun RowScope.AddItem(
    view: BottomBarView,
    currentDestination: NavDestination?,
    navController: NavHostController
) {

    val (iconBg, labelColor) =
        if (navController.currentDestination?.route == view.route) {
            Colors.lightPrimaryMain to Colors.textDefault
        } else {
            Colors.defaultBg to Colors.textSecondary
        }

    BottomNavigationItem(
        alwaysShowLabel = false,
        icon = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(width = 64.dp, height = 32.dp)
                        .background(
                            color = iconBg,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(view.iconRes),
                        contentDescription = "Navigation Icon"
                    )
                }

                SpacerHeight(height = 8.dp)

                Text(
                    text = stringResource(id = view.nameRes),
                    color = labelColor,
                    style = TextStyle.footnote
                )
            }

        },
        selected = currentDestination?.hierarchy?.any {
            it.route?.contains(view.route) == true
        } == true,
        selectedContentColor = Colors.primaryMain,
        unselectedContentColor = Colors.secondaryMain,
        onClick = {
            if (navController.currentDestination?.route != view.route) {
                navController.navigate(view.route) {
                    popUpTo(HomeRoute.endpoint())
                    launchSingleTop = true
                }
            }
        }
    )
}
