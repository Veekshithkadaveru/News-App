package com.example.newsapp.ui.filters.country

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.data.model.Country
import com.example.newsapp.ui.MainViewmodel
import com.example.newsapp.ui.base.CountryHandler
import com.example.newsapp.ui.base.DismissHandler
import com.example.newsapp.ui.base.ProgressLoading
import com.example.newsapp.ui.base.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountriesBottomSheet(
    context: Context,
    countriesViewmodel: CountriesViewmodel,
    mainViewmodel: MainViewmodel,
    onDismiss: DismissHandler
) {
    val countriesState by countriesViewmodel.countriesList.collectAsState()

    ModalBottomSheet(onDismissRequest = { onDismiss() }) {
        LoadCountries(
            context = context,
            countriesState = countriesState,
            mainViewmodel = mainViewmodel
        ) { onDismiss }

    }
}

@Composable
fun LoadCountries(
    context: Context,
    countriesState: UiState<List<Country>>,
    mainViewmodel: MainViewmodel,
    onDismiss: DismissHandler
) {
    when (countriesState) {
        is UiState.Success -> {
            if (countriesState.data.isNotEmpty()) {
                CountryList(countries = countriesState.data) { country ->
                    mainViewmodel.apply {
                        clearSelectedLanguage()
                        clearSelectedSource()
                        updateSelectedCountry(country)
                    }
                }
            }
        }

        is UiState.Loading -> {
            Box(
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth()
            ) {
                ProgressLoading(modifier = Modifier.align(Alignment.Center))
            }
        }

        is UiState.Error -> {
            Toast.makeText(context, countriesState.message, Toast.LENGTH_SHORT).show()
            onDismiss()
        }
    }
}

@Composable
private fun CountryList(
    countries: List<Country>,
    onCountrySelected: CountryHandler
) {
    LazyColumn {
        items(countries, key = { country -> country.code }) { country ->
            CountryItem(country = country) { onCountrySelected(it) }
        }
    }
}

@Composable
fun CountryItem(country: Country, onCountrySelected: CountryHandler) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable { onCountrySelected(country) },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(text = country.flag, fontSize = 24.sp)

        Spacer(modifier = Modifier.width(12.dp))

        Text(text = country.name, fontSize = 16.sp)
    }

    HorizontalDivider(thickness = 1.dp)
}
