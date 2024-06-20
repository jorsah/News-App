package com.example.newsapp.features.article.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.newsapp.features.article.ArticleScreen
import com.example.navigation.NavigationDestination
import com.example.navigation.NavigationPayload
import com.example.navigation.NavigationRoute
import com.example.remote.model.Article

object ArticleRoute : NavigationRoute.ScreenRoute() {

    override fun endpoint(): String = "article"

    @Composable
    override fun View(navController: NavController) {
        ArticleScreen(navController)
    }

    sealed class Entry : NavigationDestination {

        data class OpenWithPayload(
            private val article: Article
        ) : Entry() {
            override fun getRoute(): NavigationRoute = ArticleRoute

            override fun getPayload(): NavigationPayload = ArticlePayload(article)
        }
    }
}
