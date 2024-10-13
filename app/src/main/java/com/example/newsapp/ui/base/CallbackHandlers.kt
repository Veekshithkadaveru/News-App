package com.example.newsapp.ui.base

import com.example.newsapp.data.model.Country
import com.example.newsapp.data.model.Language
import com.example.newsapp.data.remote.model.Source

typealias ClickHandler = () -> Unit
typealias RetryHandler = () -> Unit
typealias DismissHandler = () -> Unit
typealias SearchHandler = (String) -> Unit
typealias UrlHandler = (String) -> Unit
typealias HeadlineHandler<T> = (headline: T) -> Unit
typealias SourceHandler = (Source) -> Unit
typealias LanguageHandler = (Language) -> Unit
typealias CountryHandler = (Country) -> Unit