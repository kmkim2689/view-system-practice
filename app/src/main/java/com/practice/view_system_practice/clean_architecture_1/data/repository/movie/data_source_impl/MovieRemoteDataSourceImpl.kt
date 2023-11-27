package com.practice.view_system_practice.clean_architecture_1.data.repository.movie.data_source_impl

import com.practice.view_system_practice.clean_architecture_1.data.model.movie.MovieList
import com.practice.view_system_practice.clean_architecture_1.data.remote.TMDBService
import com.practice.view_system_practice.clean_architecture_1.data.repository.movie.data_source.MovieRemoteDataSource
import retrofit2.Response

class MovieRemoteDataSourceImpl(
    private val tmdbService: TMDBService,
    private val apiKey: String
) : MovieRemoteDataSource {
    override suspend fun getMovies(): Response<MovieList> {
        return tmdbService.getPopularMovies(apiKey)
    }
}