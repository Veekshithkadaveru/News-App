package com.example.newsapp.di.module

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.room.Room
import com.example.newsapp.data.local.AppDatabase
import com.example.newsapp.data.local.AppDatabaseService
import com.example.newsapp.data.local.DatabaseService
import com.example.newsapp.data.remote.AuthInterceptor
import com.example.newsapp.data.remote.model.NetworkService
import com.example.newsapp.di.ApiKey
import com.example.newsapp.di.BaseUrl
import com.example.newsapp.utils.AppConstants.API_KEY
import com.example.newsapp.utils.DefaultDispatcherProvider
import com.example.newsapp.utils.DispatcherProvider
import com.example.newsapp.utils.network.NetworkConnected
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideNetworkConnected(@ApplicationContext context: Context): NetworkConnected {
        return NetworkConnected(context)
    }

    @Singleton
    @Provides
    fun provideCustomTabsIntent(): CustomTabsIntent {
        return CustomTabsIntent.Builder().build()
    }

    @BaseUrl
    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return "https://newsapi.org/v2/"
    }

    @ApiKey
    @Singleton
    @Provides
    fun provideApiKey(): String {
        return API_KEY
    }

    @Singleton
    @Provides
    fun provideNetworkService(@BaseUrl baseUrl: String, @ApiKey apiKey: String): NetworkService {
        val authInterceptor = AuthInterceptor(apiKey = apiKey)

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

        val contentType = "application/json".toMediaType()

        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(NetworkService::class.java)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "News App"
        ).build()
    }

    @Singleton
    @Provides
    fun providesDatabaseService(appDatabase: AppDatabase): DatabaseService {
        return AppDatabaseService(appDatabase)
    }

    @Singleton
    @Provides
    fun provideDispatchProvider(): DispatcherProvider = DefaultDispatcherProvider()

}