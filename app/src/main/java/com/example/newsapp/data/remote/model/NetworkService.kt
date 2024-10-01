package com.example.newsapp.data.remote.model

import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {
    @GET("top-headlines")
    suspend fun getHeadlinesByCountry(
        @Query("country") countryCode: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): HeadlineResponse

    @GET("top-headlines")
    suspend fun getHeadlinesBySource(
        @Query("sources") sourceId: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): HeadlineResponse

    @GET("top-headlines")
    suspend fun getHeadlinesByLanguage(
        @Query("country") countryCode: String,
        @Query("language") languageCode: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): HeadlineResponse

    @GET("top-headlines/sources")
    suspend fun getSources(
        @Query("country") countryCode: String
    ): SourceResponse

    @GET("everything")
    suspend fun search(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): HeadlineResponse
}