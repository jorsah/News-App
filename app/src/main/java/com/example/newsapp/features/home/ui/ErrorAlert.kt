package com.example.newsapp.features.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.newsapp.features.home.HomeScreenViewModel
import com.example.uikit.style.Colors
import com.example.uikit.style.TextStyle

@Composable
fun ErrorAlert(uiState: HomeScreenViewModel.UIState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(25.dp)
            .background(
                if (uiState.isOffline) {
                    Colors.headerBg
                } else Colors.errorBg
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = uiState.errorRes?.let { stringResource(id = it) }.orEmpty(),
            color = Colors.textDefault,
            style = TextStyle.footnote
        )
    }
}
