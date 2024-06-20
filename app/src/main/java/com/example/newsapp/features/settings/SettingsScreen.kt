package com.example.newsapp.features.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.datastore.model.ListType
import com.example.uikit.base.BackUIEvent
import com.example.uikit.base.BaseScreen
import com.example.uikit.base.BaseUIEvent
import com.example.uikit.base.RadioItem
import com.example.uikit.base.TopAppBar
import com.example.uikit.style.StyledKit
import com.example.uikit.style.TextStyle.defaultSmall
import com.example.uikit.utills.SpacerFill
import com.example.uikit.utills.Strings

@Composable
fun SettingsScreen(navController: NavController) {
    val viewModel = hiltViewModel<SettingsScreenViewModel>()

    BaseScreen(viewModel = viewModel, navController = navController) {
        InternalSettingsScreen(
            uiState = viewModel.uiState.value,
            onUIEvent = viewModel::onUIEvent
        )
    }
}

@Composable
private fun InternalSettingsScreen(
    uiState: SettingsScreenViewModel.UIState,
    onUIEvent: (BaseUIEvent) -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {
        StyledKit.TopAppBar(
            title = stringResource(id = Strings.settings_screen_title),
            hasBackButton = true,
            onBackButtonClick = {
                onUIEvent(BackUIEvent)
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = stringResource(id = Strings.settings_screen_title), style = defaultSmall)

            ListType.values().forEach {
                StyledKit.RadioItem(
                    modifier = Modifier.padding(vertical = 16.dp),
                    title = stringResource(id = it.nameRes),
                    isSelected = it == uiState.listType,
                    onClick = {
                        onUIEvent(SettingsScreenViewModel.UIEvent.OnSelectionChange(it))
                    }
                )
            }

            SpacerFill()
        }
    }
}
