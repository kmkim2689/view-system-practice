package com.practice.view_system_practice.clean_architecture_1.data.repository.artist.data_srouce_impl

import com.practice.view_system_practice.clean_architecture_1.data.model.artist.ArtistList
import com.practice.view_system_practice.clean_architecture_1.data.remote.TMDBService
import com.practice.view_system_practice.clean_architecture_1.data.repository.artist.data_source.ArtistRemoteDataSource
import retrofit2.Response

class ArtistRemoteDataSourceImpl(
    private val api: TMDBService,
    private val apiKey: String
) : ArtistRemoteDataSource {
    override suspend fun getArtists(): Response<ArtistList> {
        return api.getPopularArtists(apiKey)
    }
}