package com.example.newsapp.ui.headlines.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.local.entity.BookmarkHeadline
import com.example.newsapp.data.repository.Repository
import com.example.newsapp.ui.base.UiState
import com.example.newsapp.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewmodel @Inject constructor(
    private val repository: Repository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _headlineList = MutableStateFlow<UiState<List<BookmarkHeadline>>>(UiState.Loading)
    val headlineList: StateFlow<UiState<List<BookmarkHeadline>>> = _headlineList

    init {
        getBookmarkedHeadlines()
    }

    private fun getBookmarkedHeadlines() {
        viewModelScope.launch(dispatcherProvider.main) {
            repository.getBookmarkedHeadlines()
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _headlineList.value = UiState.Error(e.message.toString())
                }
                .collect {
                    _headlineList.value = UiState.Success(it)
                }
        }
    }

    fun removeFromBookmarkedHeadlines(headline: BookmarkHeadline) {
        viewModelScope.launch { repository.removeFromBookmarkHeadlines(headline = headline) }
    }
}