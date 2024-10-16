package com.example.newsapp.ui.headlines.search

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.R
import com.example.newsapp.ui.MainViewmodel
import com.example.newsapp.ui.base.BackButton
import com.example.newsapp.ui.base.ClickHandler
import com.example.newsapp.ui.base.NoNetworkStatusBar
import com.example.newsapp.ui.base.Route
import com.example.newsapp.ui.base.SearchHandler
import com.example.newsapp.ui.headlines.LoadPaginatedHeadlines
import com.example.newsapp.utils.StringsHelper.StringsHelper.SAVED_TO_BOOKMARK


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    context: Context,
    navController: NavController,
    mainViewmodel: MainViewmodel,
    searchViewmodel: SearchViewmodel = hiltViewModel(),
    onHeadlineClicked: (String) -> Unit
) {

    val networkConnectedState by mainViewmodel.isNetworkConnected.collectAsState()

    val searchHeadlinesState = searchViewmodel.searchedHeadlines.collectAsLazyPagingItems()

    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(searchQuery) { searchViewmodel.search(query = searchQuery) }

    Scaffold(topBar = {
        TopAppBar(onBackPressed = { navController.navigateUp() },
            onQueryChange = { searchQuery = it })
    }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (!networkConnectedState) {

                NoNetworkStatusBar {
                    navController.navigate(Route.OfflineScreen.name)
                }
                if (searchQuery.isNotEmpty()) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        LoadPaginatedHeadlines(
                            headlines = searchHeadlinesState,
                            isNetworkConnected = networkConnectedState,
                            bookmarkIcon = painterResource(id = R.drawable.add),
                            onHeadlineClicked = { onHeadlineClicked(it.url) },
                            onBookmarkClicked = {
                                searchViewmodel.bookmarkHeadline(it)
                                Toast.makeText(context, SAVED_TO_BOOKMARK, Toast.LENGTH_SHORT)
                                    .show()
                            },
                            onRetryClicked = {
                                searchViewmodel.search(searchViewmodel.queryText.value)
                            })
                    }
                }
                else {

                    searchViewmodel.clearHeadlines()

                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopAppBar(onBackPressed: ClickHandler, onQueryChange: SearchHandler) {

    androidx.compose.material3.TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BackButton { onBackPressed() }
                SearchField(onQueryChange(it))
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
    )
}

@Composable
fun SearchField(onQueryChange: SearchHandler) {

    TODO("Create Search Field")



}
