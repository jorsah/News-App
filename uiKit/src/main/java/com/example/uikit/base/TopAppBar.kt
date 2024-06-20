package com.example.uikit.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.uikit.style.Colors
import com.example.uikit.style.StyledKit
import com.example.uikit.style.TextStyle
import com.example.uikit.utills.Drawables
import com.example.uikit.utills.SpacerFill
import com.example.uikit.utills.clickable

@Composable
fun StyledKit.TopAppBar(
    title: String,
    hasBackButton: Boolean = false,
    trailingIcon: @Composable () -> Unit = {},
    onBackButtonClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Colors.headerBg)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = if (hasBackButton) 0.dp else 16.dp)
                .size(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (hasBackButton) {
                Icon(
                    modifier = Modifier.clickable { onBackButtonClick() },
                    painter = painterResource(id = Drawables.ic_back),
                    tint = Colors.primaryMain,
                    contentDescription = "Back Icon"
                )
            }

            Text(
                text = title,
                style = TextStyle.headline,
                color = Colors.textDefault,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            SpacerFill()

            trailingIcon()
        }

        content()

        Divider(
            modifier = Modifier
                .fillMaxWidth(),
            color = Colors.outlineMain,
            thickness = 1.dp
        )
    }
}
