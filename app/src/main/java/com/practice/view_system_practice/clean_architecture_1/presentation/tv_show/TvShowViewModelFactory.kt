package com.practice.view_system_practice.clean_architecture_1.presentation.tv_show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.GetTvShowsUseCase
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.UpdateTvShowsUseCase

class TvShowViewModelFactory(
    private val getTvShowsUseCase: GetTvShowsUseCase,
    private val updateTvShowsUseCase: UpdateTvShowsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TvShowViewModel::class.java)) {
            return TvShowViewModel(getTvShowsUseCase, updateTvShowsUseCase) as T
        } else {
            throw IllegalArgumentException("unknown view model class")
        }
    }
}