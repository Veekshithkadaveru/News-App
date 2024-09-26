package com.example.newsapp.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HeadlineResponse(
    @SerialName("status")
    val status: String = "",
    @SerialName("totalResults")
    val totalResults: Int = 0,
    @SerialName("articles")
    val headlines: List<Headline> = ArrayList()

)
