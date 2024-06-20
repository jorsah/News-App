package com.example.newsapp.features.settings

import androidx.lifecycle.SavedStateHandle
import com.example.newsapp.core.domain.usecases.GetListTypeUseCase
import com.example.newsapp.core.domain.usecases.SetListTypeUseCase
import com.example.datastore.model.ListType
import com.example.navigation.NavigationProvider
import com.example.uikit.base.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    navigationProvider: NavigationProvider,
    private val getListTypeUseCase: GetListTypeUseCase,
    private val setListTypeUseCase: SetListTypeUseCase,
    override val savedStateHandle: SavedStateHandle
) : BaseViewModel<SettingsScreenViewModel.UIState>(navigationProvider) {

    init {
        launchWithCatch({}){
            getListTypeUseCase().collect{ listType ->
                updateUIState { it.copy(listType = listType) }
            }
        }
    }

    override fun onUIEvent(uiEvent: BaseUIEvent) {
        when (uiEvent) {
            is UIEvent.OnSelectionChange -> reduce(uiEvent)
            else -> super.onUIEvent(uiEvent)
        }
    }

    private fun reduce(uiEvent: UIEvent.OnSelectionChange) {
        onUIState(uiState.value.copy(listType = uiEvent.listType))
        launchWithCatch({}){
            setListTypeUseCase(uiState.value.listType)
        }
    }

    data class UIState(
        val listType: ListType = ListType.COLUMN_LIST
    ) : BaseUIState

    sealed class UIEvent : BaseUIEvent {
        data class OnSelectionChange(val listType: ListType) : UIEvent()
    }

    override fun getStartUIState(): UIState = UIState()
}
