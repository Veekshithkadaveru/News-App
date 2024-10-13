package com.example.newsapp.ui.headlines.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.data.remote.model.Headline
import com.example.newsapp.data.repository.Repository
import com.example.newsapp.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val repository: Repository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _headlineList = MutableStateFlow<PagingData<Headline>>(value = PagingData.empty())

    val headlineList: StateFlow<PagingData<Headline>> = _headlineList

    private var fetchJob: Job? = null

    private fun launchFetching(block: suspend () -> Unit) {
        clearHeadlineList()
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch(dispatcherProvider.main) {
            block()
        }
    }

    fun getHeadlinesByCountry(countryCode: String) {
        launchFetching {
            repository.getHeadlinesByCountry(countryCode = countryCode).handleNewsUpdate()
        }
    }

    fun getHeadlinesBySource(sourceId: String) {
        launchFetching {
            repository.getHeadlinesBySource(sourceId = sourceId).handleNewsUpdate()
        }
    }

    fun getHeadlinesByLanguage(countryCode: String, languageCode: String) {
        launchFetching {
            repository.getHeadlinesByLanguage(
                countryCode = countryCode,
                languageCode = languageCode
            ).handleNewsUpdate()
        }
    }

    fun bookmarkHeadline(headline: Headline) {
        viewModelScope.launch {
            repository.bookmarkHeadline(headline = headline)
        }
    }

    private suspend fun Flow<PagingData<Headline>>.handleNewsUpdate() {
        this.flowOn(dispatcherProvider.io)
            .cachedIn(viewModelScope)
            .collect { _headlineList.value = it }
    }

    private fun clearHeadlineList() {
        _headlineList.update { PagingData.empty() }
    }

}