package com.practice.view_system_practice.clean_architecture_1.data.repository.movie.data_source

import com.practice.view_system_practice.clean_architecture_1.data.model.movie.Movie
import com.practice.view_system_practice.clean_architecture_1.data.model.movie.MovieList
import retrofit2.Response

interface MovieCacheDataSource {
    // save movies to cache
    suspend fun getMoviesFromCache(): List<Movie>

    // get movies to cache
    suspend fun saveMoviesToCache(movies: List<Movie>)
}