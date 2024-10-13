package com.example.newsapp.ui

import androidx.lifecycle.ViewModel
import com.example.newsapp.data.model.Country
import com.example.newsapp.data.remote.model.HeadlineParams
import com.example.newsapp.utils.AppConstants.DEFAULT_LANGUAGE_CODE
import com.example.newsapp.utils.AppConstants.DEFAULT_SOURCE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor() : ViewModel() {

    private val _isNetworkConnected = MutableStateFlow(false)

    val isNetworkConnected: StateFlow<Boolean> = _isNetworkConnected

    fun updateNetworkStatus(isNetworkConnected: Boolean) {
        _isNetworkConnected.update { isNetworkConnected }
    }

    private val _headlinesParams = MutableStateFlow(HeadlineParams())

    val headlineParams: StateFlow<HeadlineParams> = _headlinesParams

    fun updateSelectedCountry(country: Country) {
        _headlinesParams.update { it.copy(selectedCountry = country) }
    }

    fun updateSelectedLanguage(languageCode: String) {
        _headlinesParams.update { it.copy(selectedLanguageCode = languageCode) }
    }

    fun clearSelectedLanguage() {
        _headlinesParams.update { it.copy(selectedLanguageCode = DEFAULT_LANGUAGE_CODE) }
    }

    fun updateSelectedSource(source: String) {
        _headlinesParams.update { it.copy(selectedSourceId = source) }
    }

    fun clearSelectedSource(){
        _headlinesParams.update { it.copy(selectedSourceId = DEFAULT_SOURCE) }
    }

}