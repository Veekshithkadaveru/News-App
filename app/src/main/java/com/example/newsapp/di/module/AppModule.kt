package com.example.newsapp.di

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import com.example.newsapp.utils.network.NetworkConnected
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
}