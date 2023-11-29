package com.practice.view_system_practice.clean_architecture_1.presentation.tv_show

import androidx.lifecycle.liveData
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.GetTvShowsUseCase
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.UpdateTvShowsUseCase

class TvShowViewModel(
    private val getTvShowsUseCase: GetTvShowsUseCase,
    private val updateTvShowsUseCase: UpdateTvShowsUseCase
) {

    fun getTvShows() = liveData {
        val tvShows = getTvShowsUseCase.execute()
        emit(tvShows)
    }

    fun updateTvShows() = liveData {
        val updatedTvShows = updateTvShowsUseCase.execute()
        emit(updatedTvShows)
    }

}