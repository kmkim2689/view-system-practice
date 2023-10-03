package com.practice.view_system_practice.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumService {

    // use coroutines with retrofit -> suspend
    @GET("/albums") // endpoint
    suspend fun getAlbums(): Response<Albums>

    @GET("/albums") // endpoint
    suspend fun getSortedAlbums(
        @Query("userId") userId: Int
    ): Response<Albums>

    @GET("/albums/{id}") // endpoint
    suspend fun getAlbum(
        @Path("id") id: Int
    ): Response<Albums>
}