package com.example.newsapp.data.local

import com.example.newsapp.data.local.entity.BookmarkHeadline
import com.example.newsapp.data.remote.model.Headline
import kotlinx.coroutines.flow.Flow

interface DatabaseService {
    fun getCachedHeadlines(): Flow<List<Headline>>

    fun deleteAllAndInsertAllToCache(headlines: List<Headline>)

    fun cacheAll(headlines: List<Headline>)

    fun getBookmarkHeadlines(): Flow<List<BookmarkHeadline>>

    fun bookmarkHeadline(headline: Headline)

    fun removeFromBookmarkHeadline(headline: BookmarkHeadline)

}