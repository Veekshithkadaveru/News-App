package com.example.newsapp.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SourceResponse(
    @SerialName("status")
    val status: String = "",
    @SerialName("sources")
    val sources: List<Source> = ArrayList()
)
