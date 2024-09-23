package com.example.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.newsapp.data.local.entity.CacheHeadline
import kotlinx.coroutines.flow.Flow

@Dao
interface CacheHeadlinesDao {
    @Query("SELECT * FROM cached_headlines")
    fun getAll(): Flow<List<CacheHeadline>>

    @Insert
    fun addAll(headlines: List<CacheHeadline>)

    @Query("DELETE FROM cached_headlines")
    fun removeAll()

    @Transaction
    fun deleteAllAndRemoveAll(headlines: List<CacheHeadline>) {
        removeAll()
        addAll(headlines)
    }
}