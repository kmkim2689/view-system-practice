package com.practice.view_system_practice.clean_architecture_1.data.repository.artist.data_source

import com.practice.view_system_practice.clean_architecture_1.data.model.artist.Artist

interface ArtistCacheDataSource {
    suspend fun getArtistFromCache(): List<Artist>

    suspend fun updateArtistToCache(artists: List<Artist>)
}