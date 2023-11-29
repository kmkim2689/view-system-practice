package com.practice.view_system_practice.clean_architecture_1.di.core

import com.practice.view_system_practice.clean_architecture_1.domain.repository.ArtistRepository
import com.practice.view_system_practice.clean_architecture_1.domain.repository.MovieRepository
import com.practice.view_system_practice.clean_architecture_1.domain.repository.TvShowRepository
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.GetArtistsUseCase
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.GetMoviesUseCase
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.GetTvShowsUseCase
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.UpdateArtistsUseCase
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.UpdateMoviesUseCase
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.UpdateTvShowsUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Provides
    fun provideGetMoviesUseCase(movieRepository: MovieRepository): GetMoviesUseCase =
        GetMoviesUseCase(movieRepository)

    @Provides
    fun provideUpdateMoviesUseCase(movieRepository: MovieRepository): UpdateMoviesUseCase =
        UpdateMoviesUseCase(movieRepository)

    @Provides
    fun provideGetTvShowsUseCase(tvShowRepository: TvShowRepository): GetTvShowsUseCase =
        GetTvShowsUseCase(tvShowRepository)

    @Provides
    fun provideUpdateTvShowsUseCase(tvShowRepository: TvShowRepository): UpdateTvShowsUseCase =
        UpdateTvShowsUseCase(tvShowRepository)


    @Provides
    fun provideGetArtistsUseCase(artistRepository: ArtistRepository): GetArtistsUseCase =
        GetArtistsUseCase(artistRepository)

    @Provides
    fun provideUpdateArtistsUseCase(artistRepository: ArtistRepository): UpdateArtistsUseCase =
        UpdateArtistsUseCase(artistRepository)


}