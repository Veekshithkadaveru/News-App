package com.example.newsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.newsapp.ui.base.AppNavHost
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.utils.network.NetworkConnected
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkConnected: NetworkConnected

    @Inject
    lateinit var customTabsIntent: CustomTabsIntent

    private val mainViewmodel: MainViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        observeNetworkChanges()
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                AppNavHost(mainViewModel = mainViewmodel, customTabsIntent = customTabsIntent)
            }
        }
    }

    private fun observeNetworkChanges() {
        networkConnected.observe(this@MainActivity) {
            mainViewmodel.updateNetworkStatus(it)
        }
    }
}
