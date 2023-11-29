package com.practice.view_system_practice.clean_architecture_1.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.GetMoviesUseCase
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.UpdateMoviesUseCase

class MovieViewModelFactory(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val updateMoviesUseCase: UpdateMoviesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(getMoviesUseCase, updateMoviesUseCase) as T
        } else {
            throw IllegalArgumentException("unknown view model class")
        }
    }
}