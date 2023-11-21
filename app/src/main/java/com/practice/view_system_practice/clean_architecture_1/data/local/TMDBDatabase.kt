package com.practice.view_system_practice.clean_architecture_1.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practice.view_system_practice.clean_architecture_1.data.model.artist.Artist
import com.practice.view_system_practice.clean_architecture_1.data.model.movie.Movie
import com.practice.view_system_practice.clean_architecture_1.data.model.tv_show.TvShow

@Database(
    entities = [Artist::class, Movie::class, TvShow::class],
    version = 1
)
abstract class TMDBDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao
    abstract fun artistDao(): ArtistDao

}