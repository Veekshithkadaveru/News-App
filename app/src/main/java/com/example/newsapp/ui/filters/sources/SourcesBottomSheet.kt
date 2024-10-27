package com.example.newsapp.ui.filters.sources

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.data.remote.model.Source
import com.example.newsapp.ui.MainViewmodel
import com.example.newsapp.ui.base.DismissHandler
import com.example.newsapp.ui.base.ProgressLoading
import com.example.newsapp.ui.base.SourceHandler
import com.example.newsapp.ui.base.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SourcesBottomSheet(
    context: Context,
    sourcesViewmodel: SourcesViewmodel,
    mainViewmodel: MainViewmodel,
    countryCode: String,
    onDismiss: DismissHandler
) {
    LaunchedEffect(countryCode) {
        sourcesViewmodel.getSources(countryCode)
    }

    val sourcesState by sourcesViewmodel.sourceList.collectAsState()

    ModalBottomSheet(onDismissRequest = { onDismiss() }) {
       LoadSources(context = context, sourcesState = sourcesState, mainViewmodel = mainViewmodel) {
            onDismiss()
        }
    }
}

@Composable
fun LoadSources(
    context: Context,
    sourcesState: UiState<List<Source>>,
    mainViewmodel: MainViewmodel,
    onDismiss: DismissHandler
) {

    when (sourcesState) {
        is UiState.Success -> {
            if (sourcesState.data.isNotEmpty()) {
                SourceList(sourcesList = sourcesState.data) { source ->
                    mainViewmodel.apply {
                        clearSelectedSource()
                        updateSelectedSource(source.sourceId)
                    }
                    onDismiss()
                }
            }
        }

        UiState.Loading -> {
            Box(
                modifier = Modifier
                    .height(70.dp)
                    .fillMaxWidth()
            ) {
                ProgressLoading(modifier = Modifier.align(Alignment.Center))
            }
        }

        is UiState.Error -> {
            Toast.makeText(context, sourcesState.message, Toast.LENGTH_SHORT).show()
            onDismiss()
        }
    }
}

@Composable
fun SourceList(sourcesList: List<Source>, onSourceSelected: SourceHandler) {

    val updatedSourceList = listOf(Source()) + sourcesList
    LazyColumn(modifier = Modifier.padding(bottom = 16.dp)) {
        items(updatedSourceList, key = { source -> source.sourceId }) { source ->
            SourceItem(source = source) { onSourceSelected(it) }
        }
    }
}

@Composable
fun SourceItem(source: Source, onSourceSelected: SourceHandler) {

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onSourceSelected(source) },
        text = source.sourceName,
        textAlign = TextAlign.Center,
        fontSize = 18.sp
    )
    HorizontalDivider(thickness = 1.dp)
}
