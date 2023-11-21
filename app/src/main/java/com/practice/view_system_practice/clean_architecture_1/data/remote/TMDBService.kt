package com.practice.view_system_practice.clean_architecture_1.data.remote

import com.practice.view_system_practice.clean_architecture_1.data.model.artist.ArtistList
import com.practice.view_system_practice.clean_architecture_1.data.model.movie.MovieList
import com.practice.view_system_practice.clean_architecture_1.data.model.tv_show.TvShowList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface TMDBService {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Header("Authorization") authorization: String,
    ): Response<MovieList>

    @GET("/3/tv/popular")
    suspend fun getPopularTvShows(
        @Header("Authorization") authorization: String,
    ): Response<TvShowList>

    @GET("/3/person/popular")
    suspend fun getPopularArtists(
        @Header("Authorization") authorization: String,
    ): Response<ArtistList>
}