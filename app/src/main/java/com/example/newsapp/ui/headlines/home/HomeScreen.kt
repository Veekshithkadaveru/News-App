package com.example.newsapp.ui.headlines.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.R
import com.example.newsapp.data.remote.model.HeadlineParams
import com.example.newsapp.ui.MainViewmodel
import com.example.newsapp.ui.base.ClickHandler
import com.example.newsapp.ui.base.IconButton
import com.example.newsapp.ui.base.NoNetworkStatusBar
import com.example.newsapp.ui.base.PrimaryButton
import com.example.newsapp.ui.base.Route
import com.example.newsapp.ui.base.TextButton
import com.example.newsapp.ui.base.UrlHandler
import com.example.newsapp.ui.filters.country.CountriesBottomSheet
import com.example.newsapp.ui.filters.country.CountriesViewmodel
import com.example.newsapp.ui.filters.language.LanguageViewmodel
import com.example.newsapp.ui.filters.language.LanguagesBottomSheet
import com.example.newsapp.ui.headlines.LoadPaginatedHeadlines
import com.example.newsapp.utils.AppConstants.DEFAULT_LANGUAGE_CODE
import com.example.newsapp.utils.AppConstants.DEFAULT_SOURCE
import com.example.newsapp.utils.StringsHelper.StringsHelper.APP_NAME
import com.example.newsapp.utils.StringsHelper.StringsHelper.BACK_TO_TOP
import com.example.newsapp.utils.StringsHelper.StringsHelper.LANGUAGE_BUTTON
import com.example.newsapp.utils.StringsHelper.StringsHelper.SAVED_TO_BOOKMARK
import com.example.newsapp.utils.StringsHelper.StringsHelper.SEARCH_BUTTON
import com.example.newsapp.utils.StringsHelper.StringsHelper.SELECT_NEWS_SOURCE
import com.example.newsapp.utils.StringsHelper.StringsHelper.TOAST_NETWORK_ERROR
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    context: Context,
    navController: NavController,
    mainViewmodel: MainViewmodel,
    homeViewmodel: HomeViewmodel = hiltViewModel(),
    countriesViewmodel: CountriesViewmodel = hiltViewModel(),
    languagesViewmodel: LanguageViewmodel = hiltViewModel(),
    onHeadlineClicked: UrlHandler
) {

    val networkConnectedState by mainViewmodel.isNetworkConnected.collectAsState()

    val headlineParamsState by mainViewmodel.headlineParams.collectAsState()

    val headlinesState = homeViewmodel.headlineList.collectAsLazyPagingItems()

    val headlinesListState = rememberLazyListState()

    var showBackToTopButton by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    var showSourcesBottomSheet by remember { mutableStateOf(false) }

    var showLanguageBottomSheet by remember { mutableStateOf(false) }

    var showCountriesBottomSheet by remember { mutableStateOf(false) }

    if (networkConnectedState) {
        LaunchedEffect(headlineParamsState) {
            fetchHeadlines(headlineParamsState, homeViewmodel)
        }
    }

    LaunchedEffect(headlinesListState.isScrollInProgress) {
        if (headlinesListState.isScrollInProgress) {
            showBackToTopButton = true
        } else {
            coroutineScope.launch {
                delay(2000)
                showBackToTopButton = false
            }
        }
    }

    if (showSourcesBottomSheet) {
        if (networkConnectedState) {
            TODO("Add logic to display SourcesBottomSheet based on network connectivity")
        }
    }

    if (showLanguageBottomSheet) {
        if (networkConnectedState) {
            LanguagesBottomSheet(
                context = context,
                languageViewmodel = languagesViewmodel,
                mainViewmodel = mainViewmodel,
                selectedLanguageCode = headlineParamsState.selectedLanguageCode
            ) { showLanguageBottomSheet = false }
        } else {
            showLanguageBottomSheet = false
            Toast.makeText(context, TOAST_NETWORK_ERROR, Toast.LENGTH_SHORT).show()
        }
    }

    if (showCountriesBottomSheet) {
        if (networkConnectedState) {
            CountriesBottomSheet(
                context = context,
                countriesViewmodel = countriesViewmodel,
                mainViewmodel = mainViewmodel
            ) {
                showCountriesBottomSheet = false
            }
        } else {
            showCountriesBottomSheet = false
            Toast.makeText(context, TOAST_NETWORK_ERROR, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(topBar = {
        TopAppBar(navController = navController,
            headlinesParams = headlineParamsState,
            showLanguagesBottomSheet = { showLanguageBottomSheet = it },
            showCountriesBottomSheet = { showCountriesBottomSheet = it })
    },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Route.HomeScreen.name) }) {

                Icon(
                    painter = painterResource(id = R.drawable.bookmarks),
                    contentDescription = "Bookmark"
                )
            }
        }
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {

            if (!networkConnectedState) {
                NoNetworkStatusBar { navController.navigate(Route.OfflineScreen.name) }
            } else {
                SelectNewsSourceButton { showSourcesBottomSheet = true }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                LoadPaginatedHeadlines(
                    headlines = headlinesState,
                    isNetworkConnected = networkConnectedState,
                    bookmarkIcon = painterResource(id = R.drawable.add),
                    onHeadlineClicked = { onHeadlineClicked(it.url) },
                    onBookmarkClicked = {
                        homeViewmodel.bookmarkHeadline(it)
                        Toast.makeText(context, SAVED_TO_BOOKMARK, Toast.LENGTH_SHORT).show()
                    },
                    onRetryClicked = {
                        fetchHeadlines(
                            headlinesParams = headlineParamsState,
                            homeViewmodel = homeViewmodel
                        )
                    },
                    listState = headlinesListState,
                )

                if (showBackToTopButton) {
                    BackToTopButton(modifier = Modifier.align(Alignment.TopCenter)) {
                        coroutineScope.launch { headlinesListState.animateScrollToItem(0) }

                    }
                }
            }
        }
    }
}

private fun fetchHeadlines(headlinesParams: HeadlineParams, homeViewmodel: HomeViewmodel) {
    homeViewmodel.apply {
        when {
            headlinesParams.selectedLanguageCode != DEFAULT_LANGUAGE_CODE -> {
                getHeadlinesByLanguage(
                    languageCode = headlinesParams.selectedLanguageCode,
                    countryCode = headlinesParams.selectedCountry.code
                )
            }

            headlinesParams.selectedSourceId != DEFAULT_SOURCE -> {
                getHeadlinesBySource(sourceId = headlinesParams.selectedSourceId)
            }

            else -> {
                getHeadlinesByCountry(countryCode = headlinesParams.selectedCountry.code)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    navController: NavController,
    headlinesParams: HeadlineParams,
    showLanguagesBottomSheet: (Boolean) -> Unit,
    showCountriesBottomSheet: (Boolean) -> Unit
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(modifier = Modifier.weight(1f), text = APP_NAME)

                Row {
                    SearchButton { navController.navigate(Route.SearchScreen.name) }

                    SelectLanguageButton { showLanguagesBottomSheet(true) }

                    SelectCountryButton(flag = headlinesParams.selectedCountry.flag) {
                        showCountriesBottomSheet(true)
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        )
    )
}

@Composable
private fun SearchButton(onClick: ClickHandler) {
    IconButton(
        modifier = Modifier,
        drawablePainter = painterResource(id = R.drawable.search),
        contentDescription = SEARCH_BUTTON
    ) { onClick() }
}

@Composable
private fun SelectLanguageButton(onClick: ClickHandler) {
    IconButton(
        modifier = Modifier,
        drawablePainter = painterResource(id = R.drawable.language),
        contentDescription = LANGUAGE_BUTTON
    ) { onClick() }
}

@Composable
private fun SelectCountryButton(flag: String, onClick: ClickHandler) {
    TextButton(
        modifier = Modifier,
        text = flag
    ) { onClick() }
}

@Composable
private fun SelectNewsSourceButton(onClick: ClickHandler) {
    PrimaryButton(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        text = SELECT_NEWS_SOURCE
    ) { onClick() }
}

@Composable
private fun BackToTopButton(modifier: Modifier, onClick: ClickHandler) {
    ElevatedButton(modifier = modifier, onClick = onClick) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = R.drawable.arrow_up),
            contentDescription = BACK_TO_TOP
        )

        Text(modifier = Modifier.padding(start = 8.dp), text = BACK_TO_TOP)
    }
}
