package com.example.newsapp.ui.headlines.offline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.remote.model.Headline
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
class OfflineViewmodel @Inject constructor(
    private val repository: Repository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _headlineList = MutableStateFlow<UiState<List<Headline>>>(UiState.Loading)
    val headlineList: StateFlow<UiState<List<Headline>>> = _headlineList

    init {
        getCachedHeadlines()
    }

    private fun getCachedHeadlines() {
        viewModelScope.launch(dispatcherProvider.main) {
            repository.getCachedHeadlines()
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _headlineList.value = UiState.Error(e.toString())
                }.collect {
                    _headlineList.value = UiState.Success(it)
                }
        }
    }

    fun bookmarkHeadline(headline: Headline) {
        viewModelScope.launch { repository.bookmarkHeadline(headline) }
    }
}