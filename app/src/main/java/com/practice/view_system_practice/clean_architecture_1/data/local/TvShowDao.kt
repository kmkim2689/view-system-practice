package com.practice.view_system_practice.clean_architecture_1.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practice.view_system_practice.clean_architecture_1.data.model.artist.Artist
import com.practice.view_system_practice.clean_architecture_1.data.model.movie.Movie
import com.practice.view_system_practice.clean_architecture_1.data.model.tv_show.TvShow

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTvShows(shows: List<TvShow>)

    @Query("DELETE FROM popular_tv_shows")
    suspend fun deleteAllTvShows()

    @Query("SELECT * FROM popular_tv_shows")
    suspend fun getTvShows(): List<TvShow>
}