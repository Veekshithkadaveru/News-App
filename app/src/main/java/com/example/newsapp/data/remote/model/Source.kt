package com.example.newsapp.data.remote.model

import com.example.newsapp.data.local.entity.SourceEntity
import com.example.newsapp.data.model.SourceContract
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.intellij.lang.annotations.Flow.DEFAULT_SOURCE

@Serializable
data class Source(
    @SerialName("id")
    override val sourceId: String = DEFAULT_SOURCE,
    @SerialName("name")
    override val sourceName: String = DEFAULT_SOURCE,
) : SourceContract

fun Source.toSourceEntity(): SourceEntity {
    return SourceEntity(
        sourceId = sourceId,
        sourceName = sourceName
    )
}