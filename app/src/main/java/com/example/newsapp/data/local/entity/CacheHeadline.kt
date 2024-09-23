package com.example.newsapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.data.model.HeadlineContract
import com.example.newsapp.data.remote.model.Headline

@Entity(tableName = "cached_headlines")
data class CacheHeadline(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "url")
    override val url: String,
    @ColumnInfo(name = "author")
    override val author: String,
    @ColumnInfo(name = "title")
    override val title: String,
    @ColumnInfo(name = "description")
    override val description: String,
    @ColumnInfo(name = "imageUrl")
    override val imageUrl: String,
    @ColumnInfo(name = "publishedAt")
    override val publishedAt: String,
    @Embedded override val source: SourceEntity
) : HeadlineContract

fun CacheHeadline.toHeadline(): Headline {
    return Headline(
        url = url,
        author = author,
        title = title,
        description = description,
        imageUrl = imageUrl,
        publishedAt = publishedAt,
        source = source.toSource()
    )
}