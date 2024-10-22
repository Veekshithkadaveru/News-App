package com.example.newsapp.ui.filters.country

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.Country
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
class CountriesViewmodel @Inject constructor(
    private val repository: Repository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _countriesList = MutableStateFlow<UiState<List<Country>>>(UiState.Loading)

    val countriesList: StateFlow<UiState<List<Country>>> = _countriesList

    init {
        getAllCountries()
    }

    private fun getAllCountries() {
        viewModelScope.launch(dispatcherProvider.main) {
            repository.getAllCountries()
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _countriesList.value = UiState.Error(e.message.toString())
                }.collect{
                    _countriesList.value=UiState.Success(it)
                }
        }
    }
}