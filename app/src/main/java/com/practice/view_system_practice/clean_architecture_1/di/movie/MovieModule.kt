package com.practice.view_system_practice.clean_architecture_1.di.movie

import com.practice.view_system_practice.clean_architecture_1.domain.use_case.GetArtistsUseCase
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.GetMoviesUseCase
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.UpdateArtistsUseCase
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.UpdateMoviesUseCase
import com.practice.view_system_practice.clean_architecture_1.presentation.artist.ArtistViewModelFactory
import com.practice.view_system_practice.clean_architecture_1.presentation.movie.MovieViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MovieModule {

    @MovieScope
    @Provides
    fun provideMovieViewModelFactory(
        getMoviesUseCase: GetMoviesUseCase,
        updateMoviesUseCase: UpdateMoviesUseCase
    ): MovieViewModelFactory = MovieViewModelFactory(
        getMoviesUseCase, updateMoviesUseCase
    )
}