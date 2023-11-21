package com.practice.view_system_practice.clean_architecture_1.domain.use_case

import com.practice.view_system_practice.clean_architecture_1.data.model.movie.Movie
import com.practice.view_system_practice.clean_architecture_1.domain.repository.MovieRepository

class GetMoviesUseCase(
    private val movieRepository: MovieRepository
) {

    suspend fun execute(): List<Movie>? = movieRepository.getMovies()

}