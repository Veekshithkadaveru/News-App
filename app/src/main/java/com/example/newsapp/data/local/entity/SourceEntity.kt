package com.example.newsapp.data.local.entity

import androidx.room.ColumnInfo
import com.example.newsapp.data.model.SourceContract
import com.example.newsapp.data.remote.model.Source
import org.intellij.lang.annotations.Flow.DEFAULT_SOURCE

data class SourceEntity(
    @ColumnInfo(name = "sourceId")
    override val sourceId: String = DEFAULT_SOURCE,
    @ColumnInfo(name = "sourceName")
    override val sourceName: String = DEFAULT_SOURCE
) : SourceContract

fun SourceEntity.toSource(): Source {
    return Source(
        sourceId = sourceId,
        sourceName = sourceName
    )
}
