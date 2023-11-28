package com.practice.view_system_practice.clean_architecture_1.data.repository.artist.data_source

import com.practice.view_system_practice.clean_architecture_1.data.model.artist.ArtistList
import retrofit2.Response
import retrofit2.http.Header

interface ArtistRemoteDataSource {
    suspend fun getArtists(): Response<ArtistList>
}