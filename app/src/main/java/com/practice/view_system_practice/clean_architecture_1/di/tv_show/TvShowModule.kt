package com.practice.view_system_practice.clean_architecture_1.di.tv_show

import com.practice.view_system_practice.clean_architecture_1.domain.use_case.GetMoviesUseCase
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.GetTvShowsUseCase
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.UpdateMoviesUseCase
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.UpdateTvShowsUseCase
import com.practice.view_system_practice.clean_architecture_1.presentation.movie.MovieViewModelFactory
import com.practice.view_system_practice.clean_architecture_1.presentation.tv_show.TvShowViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class TvShowModule {

    @TvShowScope
    @Provides
    fun provideTvShowViewModelFactory(
        getTvShowsUseCase: GetTvShowsUseCase,
        updateTvShowsUseCase: UpdateTvShowsUseCase
    ): TvShowViewModelFactory = TvShowViewModelFactory(
        getTvShowsUseCase, updateTvShowsUseCase
    )
}