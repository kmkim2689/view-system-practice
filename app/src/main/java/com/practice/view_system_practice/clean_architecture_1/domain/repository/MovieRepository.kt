package com.practice.view_system_practice.clean_architecture_1.domain.repository

import com.practice.view_system_practice.clean_architecture_1.data.model.movie.Movie

interface MovieRepository {

    suspend fun getMovies(): List<Movie>?

    suspend fun updateMovies(): List<Movie>?
}