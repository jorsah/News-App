package com.example.newsapp.features.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.features.home.model.ArticleUI
import com.example.remote.model.Fields
import com.example.uikit.style.Colors
import com.example.uikit.style.TextStyle
import com.example.uikit.utills.SpacerHeight
import com.example.uikit.utills.clickable

@Preview(showBackground = true)
@Composable
fun ArticleListSmallItem(
    modifier: Modifier = Modifier,
    article: ArticleUI = ArticleUI(
        id = "asf",
        webPublicationDate = "12/05/2024",
        webTitle = "Very interesting title",
        fields = Fields(
            headline = "headline",
            thumbnail = "https://media.guim.co.uk/5956e4a8a83e4fc531927e7bf65d3c9b1099acab/61_0_3378_2027/500.jpg",
            body = ""
        )
    ),
    onDownloadClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onItemClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(color = Colors.cardBg, shape = RoundedCornerShape(12.dp))
            .padding(8.dp)
            .clickable { onItemClicked() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(72.dp)
                    .clip(CircleShape),
                model = ImageRequest.Builder(LocalContext.current)
                    .error(android.R.drawable.stat_notify_error)
                    .data(article.fields?.thumbnail)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(android.R.drawable.ic_menu_gallery),
                contentDescription = "Item image",
                contentScale = ContentScale.Crop
            )


            if (article.isDownloaded) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clickable {
                            onDeleteClick()
                        }
                        .size(36.dp),
                    painter = painterResource(id = android.R.drawable.ic_menu_delete),
                    contentDescription = "Delete Icon",
                    tint = Color.Black
                )
            } else {
                Icon(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clickable {
                            onDownloadClick()
                        }
                        .size(36.dp),
                    painter = painterResource(id = com.example.uikit.R.drawable.ic_download),
                    contentDescription = "Download Icon"
                )
            }
        }

        SpacerHeight(height = 8.dp)

        Text(
            text = article.webTitle,
            color = Colors.textDefault,
            style = TextStyle.footnote
        )

        Text(
            text = article.webPublicationDate,
            color = Colors.textSecondary,
            style = TextStyle.footnote
        )
    }
}