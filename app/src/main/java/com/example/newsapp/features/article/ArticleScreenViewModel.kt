package com.example.newsapp.features.article

import androidx.lifecycle.SavedStateHandle
import com.example.newsapp.features.article.navigation.ArticlePayload
import com.example.navigation.NavigationProvider
import com.example.remote.model.Article
import com.example.uikit.base.BaseUIEvent
import com.example.uikit.base.BaseUIState
import com.example.uikit.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleScreenViewModel @Inject constructor(
    navigationProvider: NavigationProvider,
    override val savedStateHandle: SavedStateHandle
) : BaseViewModel<ArticleScreenViewModel.UIState>(navigationProvider) {

    init {
        getPayload<ArticlePayload>(ArticlePayload.PAYLOAD_KEY) {
            onUIState(
                uiState.value.copy(
                    article = it.article
                )
            )
        }
    }

    override fun onUIEvent(uiEvent: BaseUIEvent) {
        when (uiEvent) {
            else -> super.onUIEvent(uiEvent)
        }
    }

    data class UIState(
        val article: Article? = null
    ) : BaseUIState

    override fun getStartUIState(): UIState = UIState()
}
