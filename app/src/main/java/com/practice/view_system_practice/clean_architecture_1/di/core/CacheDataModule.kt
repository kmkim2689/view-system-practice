package com.practice.view_system_practice.clean_architecture_1.di.core

import com.practice.view_system_practice.clean_architecture_1.data.repository.artist.data_source.ArtistCacheDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.artist.data_srouce_impl.ArtistCacheDataSourceImpl
import com.practice.view_system_practice.clean_architecture_1.data.repository.movie.data_source.MovieCacheDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.movie.data_source_impl.MovieCacheDataSourceImpl
import com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.data_source.TvShowCacheDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.data_source_impl.TvShowCacheDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheDataModule {

    @Provides
    @Singleton
    fun provideMovieCacheDataSource(): MovieCacheDataSource =
        MovieCacheDataSourceImpl()

    @Provides
    @Singleton
    fun provideTvShowCacheDataSource(): TvShowCacheDataSource =
        TvShowCacheDataSourceImpl()

    @Provides
    @Singleton
    fun provideArtistCacheDataSource(): ArtistCacheDataSource =
        ArtistCacheDataSourceImpl()


}