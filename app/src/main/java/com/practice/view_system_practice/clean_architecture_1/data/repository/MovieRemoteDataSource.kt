package com.practice.view_system_practice.clean_architecture_1.data.repository

import com.practice.view_system_practice.clean_architecture_1.data.model.movie.MovieList
import retrofit2.Response

interface MovieRemoteDataSource {
    suspend fun getMovies(): Response<MovieList>
}