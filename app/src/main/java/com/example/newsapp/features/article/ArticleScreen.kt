package com.example.newsapp.features.article

import android.text.util.Linkify
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.uikit.base.BackUIEvent
import com.example.uikit.base.BaseScreen
import com.example.uikit.base.BaseUIEvent
import com.example.uikit.base.TopAppBar
import com.example.uikit.style.Colors
import com.example.uikit.style.StyledKit
import com.example.uikit.style.TextStyle
import com.example.uikit.utills.SpacerHeight
import com.google.android.material.textview.MaterialTextView

@Composable
fun ArticleScreen(navController: NavController) {
    val viewModel = hiltViewModel<ArticleScreenViewModel>()

    BaseScreen(viewModel = viewModel, navController = navController) {
        InternalArticleScreen(
            uiState = viewModel.uiState.value,
            onUIEvent = viewModel::onUIEvent
        )
    }
}

@Composable
private fun InternalArticleScreen(
    uiState: ArticleScreenViewModel.UIState,
    onUIEvent: (BaseUIEvent) -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {
        StyledKit.TopAppBar(
            title = uiState.article?.webTitle.orEmpty(),
            hasBackButton = true,
            onBackButtonClick = { onUIEvent(BackUIEvent) }
        )

        val spannedText = remember {
            HtmlCompat.fromHtml(uiState.article?.fields?.body.orEmpty(), 0)
        }

        Column(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = uiState.article?.webTitle.orEmpty(),
                color = Colors.textDefault,
                style = TextStyle.footnote,
                textAlign = TextAlign.Center
            )

            AsyncImage(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(uiState.article?.fields?.thumbnail)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(android.R.drawable.ic_menu_gallery),
                contentDescription = "Item image",
                contentScale = ContentScale.Crop
            )

            uiState.article?.webPublicationDate?.let {
                Text(
                    text = it,
                    color = Colors.textSecondary,
                    style = TextStyle.footnote
                )
            }

            SpacerHeight(height = 8.dp)

            AndroidView(
                factory = {
                    MaterialTextView(it).apply {
                        // links
                        autoLinkMask = Linkify.WEB_URLS
                        linksClickable = true
                        setLinkTextColor(Color.Magenta.toArgb())

                        setTextColor(Colors.textDefault.toArgb())
                    }
                },
                update = {
                    it.text = spannedText
                }
            )
        }
    }
}
