package com.example.newsapp.features.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsapp.features.home.HomeScreenViewModel
import com.example.uikit.base.BaseUIEvent
import com.example.uikit.utills.SpacerHeight
import com.example.uikit.utills.SpacerWidth

@Composable
internal fun ColumnScope.GridList(
    uiState: HomeScreenViewModel.UIState,
    onUIEvent: (BaseUIEvent) -> Unit
) {
    val firstColumn = remember(uiState.articles) {
        mutableStateOf(uiState.articles.filterIndexed { index, _ -> index % 2 == 1 })
    }

    val secondColumn = remember(uiState.articles) {
        mutableStateOf(uiState.articles.filterIndexed { index, _ -> index % 2 == 0 })
    }
    val scrollState = rememberScrollState()

    LaunchedEffect(scrollState.canScrollForward) {
        if (!scrollState.canScrollForward) {
            onUIEvent(HomeScreenViewModel.UIEvent.OnLoadMore)
        }
    }

    Row(
        modifier = Modifier.Companion
            .weight(1f)
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .fillMaxWidth()
                .weight(1f),
        ) {
            firstColumn.value.forEach {
                ArticleListSmallItem(
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

        SpacerWidth(width = 8.dp)

        Column(
            modifier = Modifier
                .padding(end = 16.dp, top = 16.dp)
                .fillMaxWidth()
                .weight(1f),
        ) {
            secondColumn.value.forEach {
                ArticleListSmallItem(
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
}
