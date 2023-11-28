package com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.data_source

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practice.view_system_practice.clean_architecture_1.data.model.tv_show.TvShow

interface TvShowLocalDataSource {
    suspend fun saveTvShowsToDb(shows: List<TvShow>)

    suspend fun clearAll()

    suspend fun getTvShowsFromDb(): List<TvShow>
}