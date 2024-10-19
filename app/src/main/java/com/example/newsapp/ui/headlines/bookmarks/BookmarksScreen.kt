package com.example.newsapp.ui.headlines.bookmarks

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsapp.ui.base.BackButton
import com.example.newsapp.ui.base.ClickHandler
import com.example.newsapp.ui.base.UrlHandler
import com.example.newsapp.utils.StringsHelper.StringsHelper.BOOKMARKED_NEWS

@Composable
fun BookmarksScreen(
    context: Context,
    navController: NavController,
    bookmarksViewmodel: BookmarksViewmodel = hiltViewModel(),
    onHeadlineClicked: UrlHandler
) {

    val savedHeadlinesState by bookmarksViewmodel.headlineList.collectAsState()

    TODO("Added Scaffold with TopAppBar")

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(onBackPressed: ClickHandler) {

    androidx.compose.material3.TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BackButton { onBackPressed() }

                Text(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f),
                    text = BOOKMARKED_NEWS
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}