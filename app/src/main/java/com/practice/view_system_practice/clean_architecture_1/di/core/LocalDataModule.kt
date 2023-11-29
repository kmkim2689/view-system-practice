package com.practice.view_system_practice.clean_architecture_1.di.core

import com.practice.view_system_practice.clean_architecture_1.data.local.ArtistDao
import com.practice.view_system_practice.clean_architecture_1.data.local.MovieDao
import com.practice.view_system_practice.clean_architecture_1.data.local.TvShowDao
import com.practice.view_system_practice.clean_architecture_1.data.repository.artist.data_source.ArtistLocalDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.artist.data_srouce_impl.ArtistLocalDataSourceImpl
import com.practice.view_system_practice.clean_architecture_1.data.repository.movie.data_source.MovieLocalDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.movie.data_source_impl.MovieLocalDataSourceImpl
import com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.data_source.TvShowLocalDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.data_source_impl.TvShowLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataModule {

    @Provides
    @Singleton
    fun provideMovieLocalDataSource(movieDao: MovieDao): MovieLocalDataSource =
        MovieLocalDataSourceImpl(movieDao)

    @Provides
    @Singleton
    fun provideTvShowLocalDataSource(tvShowDao: TvShowDao): TvShowLocalDataSource =
        TvShowLocalDataSourceImpl(tvShowDao)

    @Provides
    @Singleton
    fun provideArtistLocalDataSource(artistDao: ArtistDao): ArtistLocalDataSource =
        ArtistLocalDataSourceImpl(artistDao)



}