package com.practice.view_system_practice.clean_architecture_1.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practice.view_system_practice.clean_architecture_1.data.model.artist.Artist
import com.practice.view_system_practice.clean_architecture_1.data.model.movie.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<Movie>)

    @Query("DELETE FROM popular_movies")
    suspend fun deleteAllMovies()

    @Query("SELECT * FROM popular_movies")
    suspend fun getMovies(): List<Movie>
}