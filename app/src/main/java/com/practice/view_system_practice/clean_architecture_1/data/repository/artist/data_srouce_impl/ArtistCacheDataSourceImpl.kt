package com.practice.view_system_practice.clean_architecture_1.data.repository.artist.data_srouce_impl

import com.practice.view_system_practice.clean_architecture_1.data.model.artist.Artist
import com.practice.view_system_practice.clean_architecture_1.data.repository.artist.data_source.ArtistCacheDataSource

class ArtistCacheDataSourceImpl(

) : ArtistCacheDataSource {

    private var artistList = ArrayList<Artist>()

    override suspend fun getArtistFromCache(): List<Artist> {
        return artistList
    }

    override suspend fun updateArtistToCache(artists: List<Artist>) {
        artistList.clear()
        artistList = ArrayList(artists)
    }
}