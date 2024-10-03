package com.example.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.data.local.DatabaseService
import com.example.newsapp.data.local.entity.BookmarkHeadline
import com.example.newsapp.data.model.Country
import com.example.newsapp.data.model.HeadlineQuery
import com.example.newsapp.data.model.Language
import com.example.newsapp.data.paging.HeadlinePagingSource
import com.example.newsapp.data.remote.NetworkService
import com.example.newsapp.data.remote.model.Headline
import com.example.newsapp.data.remote.model.Source
import com.example.newsapp.utils.AppConstants.PAGE_SIZE
import com.example.newsapp.utils.CountryHelper
import com.example.newsapp.utils.DispatcherProvider
import com.example.newsapp.utils.LanguageHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
    private val dispatcherProvider: DispatcherProvider
) {

    fun getAllCountries(): Flow<List<Country>> {
        return flow { emit(CountryHelper.getCountries()) }.map { it.countries }
    }

    fun getAllLanguages(): Flow<List<Language>> {
        return flow { emit(LanguageHelper.getAllLanguages()) }
    }

    fun getHeadlinesByCountry(countryCode: String): Flow<PagingData<Headline>> {
        return getHeadlines(HeadlineQuery.ByCountry(countryCode = countryCode))
    }

    fun getHeadlinesBySource(sourceId: String): Flow<PagingData<Headline>> {
        return getHeadlines(HeadlineQuery.BySource(sourceId = sourceId))
    }

    fun getHeadlinesByLanguage(
        countryCode: String,
        languageCode: String
    ): Flow<PagingData<Headline>> {
        return getHeadlines(
            HeadlineQuery.ByLanguage(
                countryCode = countryCode,
                languageCode = languageCode
            )
        )
    }

    fun search(query: String): Flow<PagingData<Headline>> {
        return getHeadlines(HeadlineQuery.BySearch(query = query))
    }

    private fun getHeadlines(headlineQuery: HeadlineQuery): Flow<PagingData<Headline>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                HeadlinePagingSource(
                    networkService = networkService,
                    databaseService = databaseService,
                    dispatcherProvider = dispatcherProvider,
                    query = headlineQuery
                )
            }
        ).flow
    }

    fun getSources(countryCode: String): Flow<List<Source>> {
        return flow {
            emit(networkService.getSources(countryCode = countryCode))
        }.map { it.sources }
    }

    suspend fun getCachedHeadlines(): Flow<List<Headline>> {
        return withContext(dispatcherProvider.io) { databaseService.getCachedHeadlines() }

    }

    suspend fun bookmarkHeadline(headline: Headline) {
        withContext(dispatcherProvider.io) { databaseService.bookmarkHeadline(headline = headline) }
    }

    suspend fun removeFromBookmarkHeadlines(headline: BookmarkHeadline) {
        withContext(dispatcherProvider.io) { databaseService.removeFromBookmarkHeadline(headline = headline) }
    }

    suspend fun getBookmarkedHeadlines(): Flow<List<BookmarkHeadline>> {
        return withContext(dispatcherProvider.io) { databaseService.getBookmarkHeadlines() }
    }

}