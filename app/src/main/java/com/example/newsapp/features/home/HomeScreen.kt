package com.example.newsapp.features.home

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.uikit.R
import com.example.newsapp.features.home.ui.EndlessColumnList
import com.example.newsapp.features.home.ui.ErrorAlert
import com.example.newsapp.features.home.ui.GridList
import com.example.datastore.model.ListType
import com.example.uikit.base.BaseScreen
import com.example.uikit.base.BaseUIEvent
import com.example.uikit.base.TopAppBar
import com.example.uikit.style.Colors
import com.example.uikit.style.StyledKit
import com.example.uikit.style.TextStyle
import com.example.uikit.utills.Strings
import com.example.uikit.utills.clickable

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = hiltViewModel<HomeScreenViewModel>()

    BaseScreen(viewModel = viewModel, navController = navController) {
        InternalHomeScreen(
            uiState = viewModel.uiState.value,
            onUIEvent = viewModel::onUIEvent
        )
    }
}

@Composable
private fun InternalHomeScreen(
    uiState: HomeScreenViewModel.UIState,
    onUIEvent: (BaseUIEvent) -> Unit = {}
) {

    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus()
            })
        }) {
        StyledKit.TopAppBar(
            title = stringResource(id = Strings.home_screen_title)
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Colors.defaultBg
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.search_field_placeholder),
                        color = Colors.textSecondary,
                        style = TextStyle.footnote
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .clickable {
                                onUIEvent(HomeScreenViewModel.UIEvent.OnSearchClicked)
                                focusManager.clearFocus()
                            }
                            .padding(end = 16.dp),
                        painter = painterResource(id = android.R.drawable.ic_menu_search),
                        tint = Colors.primaryMain,
                        contentDescription = "Search Icon"
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onUIEvent(HomeScreenViewModel.UIEvent.OnSearchClicked)
                        focusManager.clearFocus()
                    }
                ),
                value = uiState.searchText,
                onValueChange = {
                    onUIEvent(
                        HomeScreenViewModel.UIEvent.OnSearchTextChanged(it)
                    )
                })

            if (uiState.isError) {
                ErrorAlert(uiState)
            }
        }

        when (uiState.listType) {
            ListType.GRID_LIST -> GridList(uiState, onUIEvent)
            ListType.COLUMN_LIST -> EndlessColumnList(onUIEvent, uiState)
        }

        if (uiState.isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
            ) {
                CircularProgressIndicator(color = Colors.progressBarMain)
            }
        }
    }
}
