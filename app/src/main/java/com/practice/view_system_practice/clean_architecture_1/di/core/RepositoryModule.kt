package com.practice.view_system_practice.clean_architecture_1.di.core

import com.practice.view_system_practice.clean_architecture_1.data.repository.artist.ArtistRepositoryImpl
import com.practice.view_system_practice.clean_architecture_1.data.repository.artist.data_source.ArtistCacheDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.artist.data_source.ArtistLocalDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.artist.data_source.ArtistRemoteDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.movie.MovieRepositoryImpl
import com.practice.view_system_practice.clean_architecture_1.data.repository.movie.data_source.MovieCacheDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.movie.data_source.MovieLocalDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.movie.data_source.MovieRemoteDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.TvShowRepositoryImpl
import com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.data_source.TvShowCacheDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.data_source.TvShowLocalDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.data_source.TvShowRemoteDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.data_source_impl.TvShowRemoteDataSourceImpl
import com.practice.view_system_practice.clean_architecture_1.domain.repository.ArtistRepository
import com.practice.view_system_practice.clean_architecture_1.domain.repository.MovieRepository
import com.practice.view_system_practice.clean_architecture_1.domain.repository.TvShowRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieCacheDataSource: MovieCacheDataSource,
        movieLocalDataSource: MovieLocalDataSource,
        movieRemoteDataSource: MovieRemoteDataSource
    ): MovieRepository = MovieRepositoryImpl(
        movieRemoteDataSource,
        movieLocalDataSource,
        movieCacheDataSource
    )

    @Provides
    @Singleton
    fun provideTvShowRepository(
        tvShowCacheDataSource: TvShowCacheDataSource,
        tvShowLocalDataSource: TvShowLocalDataSource,
        tvShowRemoteDataSource: TvShowRemoteDataSource
    ): TvShowRepository = TvShowRepositoryImpl(
        tvShowCacheDataSource,
        tvShowLocalDataSource,
        tvShowRemoteDataSource
    )

    @Provides
    @Singleton
    fun provideArtistRepository(
        artistCacheDataSource: ArtistCacheDataSource,
        artistLocalDataSource: ArtistLocalDataSource,
        artistRemoteDataSource: ArtistRemoteDataSource
    ): ArtistRepository = ArtistRepositoryImpl(
        artistRemoteDataSource,
        artistLocalDataSource,
        artistCacheDataSource
    )

}