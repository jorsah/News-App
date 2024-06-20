package com.example.newsapp.features.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.uikit.R
import com.example.uikit.style.Colors
import com.example.uikit.style.TextStyle
import com.example.uikit.utills.SpacerHeight
import com.example.uikit.utills.SpacerWidth
import com.example.uikit.utills.clickable

@Preview(showBackground = true)
@Composable
fun ArticleListItem(
    article: ArticleUI = ArticleUI(
        id = "asf",
        webPublicationDate = "12/05/2024",
        webTitle = "Very interesting title",
        fields = Fields(
            headline = "headline",
            thumbnail = "https://media.guim.co.uk/5956e4a8a83e4fc531927e7bf65d3c9b1099acab/61_0_3378_2027/500.jpg",
            body = "",
        ),
        isDownloaded = true
    ),
    onDownloadClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onItemClicked: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Colors.cardBg, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(128.dp)
            .clickable { onItemClicked() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
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

        SpacerWidth(width = 16.dp)

        Column(
            modifier = Modifier
                .weight(1f),
        ) {
            Text(
                text = article.webTitle,
                color = Colors.textDefault,
                style = TextStyle.footnote
            )

            SpacerHeight(height = 8.dp)

            Text(
                text = article.webPublicationDate,
                color = Colors.textSecondary,
                style = TextStyle.footnote
            )
        }

        if (article.isDownloaded) {
            Icon(
                modifier = Modifier
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
                    .clickable {
                        onDownloadClick()
                    }
                    .size(36.dp),
                painter = painterResource(id = R.drawable.ic_download),
                contentDescription = "Download Icon"
            )
        }

    }
}
