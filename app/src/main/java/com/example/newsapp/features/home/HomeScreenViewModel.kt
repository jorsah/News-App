package com.example.newsapp.features.home

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.datastore.model.ListType
import com.example.navigation.NavigationProvider
import com.example.newsapp.core.domain.usecases.DeleteArticleUseCase
import com.example.newsapp.core.domain.usecases.DownloadArticleUseCase
import com.example.newsapp.core.domain.usecases.GetListTypeUseCase
import com.example.newsapp.core.domain.usecases.GetOfflineArticlesUseCase
import com.example.newsapp.core.domain.usecases.SearchArticlesUseCase
import com.example.newsapp.core.utils.isNetworkReachable
import com.example.newsapp.core.utils.track
import com.example.newsapp.features.article.navigation.ArticleRoute
import com.example.newsapp.features.home.model.ArticleUI
import com.example.newsapp.features.home.model.toDomain
import com.example.newsapp.features.home.model.toUI
import com.example.remote.model.Article
import com.example.uikit.R
import com.example.uikit.base.BaseUIEvent
import com.example.uikit.base.BaseUIState
import com.example.uikit.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    navigationProvider: NavigationProvider,
    private val searchArticlesUseCase: SearchArticlesUseCase,
    private val getListTypeUseCase: GetListTypeUseCase,
    private val downloadArticleUseCase: DownloadArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    getOfflineArticlesUseCase: GetOfflineArticlesUseCase,
    override val savedStateHandle: SavedStateHandle
) : BaseViewModel<HomeScreenViewModel.UIState>(navigationProvider) {

    private var currentPage: Int = 1
    private val offlineArticles: MutableList<Article> = mutableListOf()

    init {
        launchWithCatch({
            handleLoadingError(it)
            if (uiState.value.isOffline) {
                updateUIState {
                    it.copy(articles = offlineArticles.map { it.toUI(true) })
                }
            }
        }) {
            offlineArticles.addAll(getOfflineArticlesUseCase())
            val newArticles = searchArticlesUseCase(currentPage)

            onUIState(
                uiState.value.copy(
                    articles = newArticles.map { newArticle ->
                        newArticle.toUI(offlineArticles.contains(newArticle))
                    },
                )
            )

        }

        viewModelScope.launch {
            getListTypeUseCase().collect { type ->
                updateUIState { it.copy(listType = type) }
            }
        }
    }

    override fun onUIEvent(uiEvent: BaseUIEvent) {
        when (uiEvent) {
            is UIEvent.OnArticleClicked -> reduce(uiEvent)
            is UIEvent.OnSearchTextChanged -> reduce(uiEvent)
            is UIEvent.OnArticleDownloadClicked -> reduce(uiEvent)
            is UIEvent.OnArticleDeleteClicked -> reduce(uiEvent)
            is UIEvent.OnSearchClicked -> reduce(uiEvent)
            is UIEvent.OnLoadMore -> reduce(uiEvent)
            else -> super.onUIEvent(uiEvent)
        }
    }

    private fun reduce(uiEvent: UIEvent.OnArticleDeleteClicked) {
        val article = uiState.value.articles.firstOrNull { it.id == uiEvent.id } ?: return

        launchWithCatch({
            updateUIState { state ->
                state.copy(
                    isError = true,
                    errorRes = R.string.other_errors
                )
            }
        }) {
            deleteArticleUseCase(article.toDomain())

            updateUIState { state ->
                state.copy(
                    articles = state.articles.map {
                        if (it.id == uiEvent.id) it.copy(isDownloaded = false)
                        else it
                    }.filter { !state.isOffline || it.isDownloaded }
                )
            }
        }
    }

    private fun reduce(uiEvent: UIEvent.OnArticleDownloadClicked) {
        val article = uiState.value.articles.firstOrNull { it.id == uiEvent.id } ?: return

        launchWithCatch({
            updateUIState { state ->
                state.copy(
                    isError = true,
                    errorRes = R.string.other_errors
                )
            }
        }) {
            downloadArticleUseCase(article.toDomain())
            offlineArticles.add(article.toDomain())
            updateUIState { state ->
                state.copy(
                    articles = state.articles.map {
                        if (it.id == uiEvent.id) it.copy(isDownloaded = true)
                        else it
                    }
                )
            }
        }
    }

    private fun reduce(uiEvent: UIEvent.OnSearchClicked) {
        launchWithCatch({ exception ->
            handleLoadingError(exception)
            if (uiState.value.isOffline) {
                updateUIState { state ->
                    state.copy(
                        articles = offlineArticles.filter {
                            it.webTitle.contains(state.searchText)
                        }.map { it.toUI(true) }
                    )
                }
            }
        }) {
            onUIState(
                uiState.value.copy(
                    articles = searchArticlesUseCase(query = uiState.value.searchText).map {
                        it.toUI(
                            offlineArticles.contains(it)
                        )
                    },
                    isError = false,
                    isOffline = false,
                    errorRes = R.string.no_connection_error,
                )
            )
            currentPage = 1
        }.track { onUIState(uiState.value.copy(isLoading = it)) }
    }

    private fun reduce(uiEvent: UIEvent.OnSearchTextChanged) {
        updateUIState { it.copy(searchText = uiEvent.text) }
    }

    private fun reduce(uiEvent: UIEvent.OnLoadMore) {
        launchWithCatch({
            handleLoadingError(it)
        }) {
            onUIState(
                uiState.value.copy(
                    articles = uiState.value.articles + searchArticlesUseCase(currentPage + 1)
                        .map { it.toUI(
                            offlineArticles.contains(it)
                        ) },
                    isError = false,
                    isOffline = false
                )
            )
            currentPage++
        }.track { onUIState(uiState.value.copy(isLoading = it)) }
    }

    private fun handleLoadingError(it: Throwable) {
        it.isNetworkReachable(onAvailable = {
            updateUIState { state ->
                state.copy(isError = true, errorRes = R.string.loading_failed_error)
            }
        }, onError = {
            updateUIState { state ->
                state.copy(
                    isError = true,
                    errorRes = R.string.no_connection_error,
                    isOffline = true
                )
            }
        })
    }

    private fun reduce(uiEvent: UIEvent.OnArticleClicked) {
        val article = uiState.value.articles.firstOrNull { it.id == uiEvent.id } ?: return

        openDestination(
            ArticleRoute.Entry.OpenWithPayload(
                article = article.toDomain()
            )
        )
    }


    data class UIState(
        val articles: List<ArticleUI> = emptyList(),
        val isLoading: Boolean = false,
        val searchText: String = "",
        val isError: Boolean = false,
        @StringRes val errorRes: Int? = null,
        val isOffline: Boolean = false,
        val listType: ListType = ListType.COLUMN_LIST
    ) : BaseUIState

    sealed class UIEvent : BaseUIEvent {
        data class OnArticleClicked(val id: String) : UIEvent()
        data class OnArticleDownloadClicked(val id: String) : UIEvent()
        data class OnArticleDeleteClicked(val id: String) : UIEvent()
        data class OnSearchTextChanged(val text: String) : UIEvent()
        object OnSearchClicked : UIEvent()
        object OnLoadMore : UIEvent()
    }

    override fun getStartUIState(): UIState = UIState()
}
