package com.practice.view_system_practice.clean_architecture_1.di.core

import com.practice.view_system_practice.clean_architecture_1.data.remote.TMDBService
import com.practice.view_system_practice.clean_architecture_1.data.repository.artist.data_source.ArtistRemoteDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.artist.data_srouce_impl.ArtistRemoteDataSourceImpl
import com.practice.view_system_practice.clean_architecture_1.data.repository.movie.data_source.MovieRemoteDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.movie.data_source_impl.MovieRemoteDataSourceImpl
import com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.data_source.TvShowRemoteDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.data_source_impl.TvShowRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataModule(private val apiKey: String) {

    @Provides
    @Singleton
    fun provideMovieRemoteDataSource(tmdbService: TMDBService): MovieRemoteDataSource =
        MovieRemoteDataSourceImpl(tmdbService, apiKey)

    @Provides
    @Singleton
    fun provideTvShowRemoteDataSource(tmdbService: TMDBService): TvShowRemoteDataSource =
        TvShowRemoteDataSourceImpl(tmdbService, apiKey)

    @Provides
    @Singleton
    fun provideArtistRemoteDataSource(tmdbService: TMDBService): ArtistRemoteDataSource =
        ArtistRemoteDataSourceImpl(tmdbService, apiKey)


}