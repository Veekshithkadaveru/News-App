package com.example.newsapp.data.remote.model

import com.example.newsapp.data.model.Country
import com.example.newsapp.utils.AppConstants.DEFAULT_COUNTRY_CODE
import com.example.newsapp.utils.AppConstants.DEFAULT_COUNTRY_FLAG
import com.example.newsapp.utils.AppConstants.DEFAULT_COUNTRY_NAME
import com.example.newsapp.utils.AppConstants.DEFAULT_SOURCE

data class HeadlineParams(

    val selectedCountry: Country = Country(
        code = DEFAULT_COUNTRY_CODE,
        name = DEFAULT_COUNTRY_NAME,
        flag = DEFAULT_COUNTRY_FLAG
    ),
    val selectedLanguageCode: String = DEFAULT_COUNTRY_CODE,
    val selectedSourceId: String = DEFAULT_SOURCE

)
