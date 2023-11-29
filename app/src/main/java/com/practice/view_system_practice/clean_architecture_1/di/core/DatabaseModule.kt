package com.practice.view_system_practice.clean_architecture_1.di.core

import android.content.Context
import androidx.room.Room
import com.practice.view_system_practice.clean_architecture_1.data.local.ArtistDao
import com.practice.view_system_practice.clean_architecture_1.data.local.MovieDao
import com.practice.view_system_practice.clean_architecture_1.data.local.TMDBDatabase
import com.practice.view_system_practice.clean_architecture_1.data.local.TvShowDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(
        context: Context
    ): TMDBDatabase = Room.databaseBuilder(
        context,
        TMDBDatabase::class.java,
        "tmdb_db"
    ).build()

    @Provides
    @Singleton
    fun provideMovieDao(
        tmdbDatabase: TMDBDatabase
    ): MovieDao = tmdbDatabase.movieDao()

    @Provides
    @Singleton
    fun provideTvShowDao(
        tmdbDatabase: TMDBDatabase
    ): TvShowDao = tmdbDatabase.tvShowDao()

    @Provides
    @Singleton
    fun provideArtistDao(
        tmdbDatabase: TMDBDatabase
    ): ArtistDao = tmdbDatabase.artistDao()
}