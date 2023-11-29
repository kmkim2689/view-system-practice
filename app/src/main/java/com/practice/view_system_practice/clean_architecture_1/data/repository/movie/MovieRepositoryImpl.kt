package com.practice.view_system_practice.clean_architecture_1.data.repository.movie

import android.util.Log
import com.practice.view_system_practice.clean_architecture_1.data.model.movie.Movie
import com.practice.view_system_practice.clean_architecture_1.data.repository.movie.data_source.MovieCacheDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.movie.data_source.MovieLocalDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.movie.data_source.MovieRemoteDataSource
import com.practice.view_system_practice.clean_architecture_1.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieCacheDataSource: MovieCacheDataSource
) : MovieRepository {
    override suspend fun getMovies(): List<Movie>? {
        return getMoviesFromCache()
    }

    override suspend fun updateMovies(): List<Movie>? {
        val newMovieList = getMoviesFromApi()
        movieLocalDataSource.clearAll()
        movieLocalDataSource.saveMoviesToDB(newMovieList)
        movieCacheDataSource.saveMoviesToCache(newMovieList)

        return newMovieList
    }

    suspend fun getMoviesFromApi(): List<Movie> {
        var movieList: List<Movie> = emptyList()
        try {
            val response = movieRemoteDataSource.getMovies()
            val body = response.body()
            Log.i("MovieRepositoryImpl", body.toString())
            if (body != null) movieList = body.results
        } catch (e: Exception) {
            Log.i("MovieRepositoryImpl", e.message.toString())
        }

        return movieList
    }

    suspend fun getMoviesFromDb(): List<Movie> {
        lateinit var movieList: List<Movie>

        try {
            movieList = movieLocalDataSource.getMoviesFromDb()
        } catch (e: Exception) {
            Log.i("MovieRepositoryImpl", e.message.toString())
        }

        if (movieList.isNotEmpty()) {
            return movieList
        }

        movieList = getMoviesFromApi()
        movieLocalDataSource.saveMoviesToDB(movieList)

        return movieList
    }

    suspend fun getMoviesFromCache(): List<Movie> {
        lateinit var movieList: List<Movie>

        try {
            movieList = movieCacheDataSource.getMoviesFromCache()
        } catch (e: Exception) {
            Log.i("MovieRepositoryImpl", e.message.toString())
        }

        if (movieList.isNotEmpty()) {
            return movieList
        }

        movieList = getMoviesFromDb()
        movieCacheDataSource.saveMoviesToCache(movieList)

        return movieList
    }
}