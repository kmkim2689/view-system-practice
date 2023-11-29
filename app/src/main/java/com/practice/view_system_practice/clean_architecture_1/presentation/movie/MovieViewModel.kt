package com.practice.view_system_practice.clean_architecture_1.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.GetMoviesUseCase
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.UpdateMoviesUseCase

class MovieViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val updateMoviesUseCase: UpdateMoviesUseCase
) : ViewModel() {

    fun getMovies() = liveData {
        val movieList = getMoviesUseCase.execute()
        emit(movieList)
    }

    fun updateMovies() = liveData {
        val updatedMovieList = updateMoviesUseCase.execute()
        emit(updatedMovieList)
    }

}