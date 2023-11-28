package com.practice.view_system_practice.clean_architecture_1.data.repository.artist.data_srouce_impl

import com.practice.view_system_practice.clean_architecture_1.data.local.ArtistDao
import com.practice.view_system_practice.clean_architecture_1.data.model.artist.Artist
import com.practice.view_system_practice.clean_architecture_1.data.repository.artist.data_source.ArtistLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArtistLocalDataSourceImpl(
    private val dao: ArtistDao
) : ArtistLocalDataSource {
    override suspend fun saveArtists(artists: List<Artist>) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.saveArtists(artists)
        }
    }

    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteAllArtists()
        }
    }

    override suspend fun getArtists(): List<Artist> {
        return dao.getArtists()
    }
}