package com.example.newsapp.features.home.ui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsapp.features.home.HomeScreenViewModel
import com.example.newsapp.features.home.model.ArticleUI
import com.example.uikit.base.BaseUIEvent
import com.example.uikit.utills.SpacerHeight
import com.example.uikit.utills.reachedBottom

@Composable
internal fun ColumnScope.EndlessColumnList(
    onUIEvent: (BaseUIEvent) -> Unit,
    uiState: HomeScreenViewModel.UIState
) {
    val listState = rememberLazyListState()

    val reachedBottom: Boolean by remember {
        derivedStateOf { listState.reachedBottom() }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom) {
            onUIEvent(HomeScreenViewModel.UIEvent.OnLoadMore)
        }
    }

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .weight(1f),
        state = listState
    ) {
        item {
            SpacerHeight(height = 16.dp)
        }

        items(
            items = uiState.articles,
            key = { article: ArticleUI -> article.id }
        ) {
            ArticleListItem(
                article = it,
                onDeleteClick = {
                    onUIEvent(HomeScreenViewModel.UIEvent.OnArticleDeleteClicked(it.id))
                },
                onDownloadClick = {
                    onUIEvent(HomeScreenViewModel.UIEvent.OnArticleDownloadClicked(it.id))
                },
                onItemClicked = {
                    onUIEvent(HomeScreenViewModel.UIEvent.OnArticleClicked(it.id))
                }
            )

            SpacerHeight(height = 8.dp)
        }
    }
}