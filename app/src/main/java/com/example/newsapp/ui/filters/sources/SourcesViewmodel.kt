package com.example.newsapp.ui.filters.sources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.remote.model.Source
import com.example.newsapp.data.repository.Repository
import com.example.newsapp.ui.base.UiState
import com.example.newsapp.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourcesViewmodel @Inject constructor(
    private val repository: Repository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _sourceList = MutableStateFlow<UiState<List<Source>>>(UiState.Loading)

    val sourceList: StateFlow<UiState<List<Source>>> = _sourceList

    fun getSources(countryCode: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            repository.getSources(countryCode)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _sourceList.value = UiState.Error(e.message.toString())
                }.collect {
                    _sourceList.value = UiState.Success(it)
                }
        }
    }

    fun clearSources() {
        _sourceList.update { UiState.Loading }
    }
}
