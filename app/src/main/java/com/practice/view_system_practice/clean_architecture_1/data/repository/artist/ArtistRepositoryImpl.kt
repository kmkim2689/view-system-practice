package com.practice.view_system_practice.clean_architecture_1.data.repository.artist

import android.util.Log
import com.practice.view_system_practice.clean_architecture_1.data.model.artist.Artist
import com.practice.view_system_practice.clean_architecture_1.data.repository.artist.data_source.ArtistCacheDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.artist.data_source.ArtistLocalDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.artist.data_source.ArtistRemoteDataSource
import com.practice.view_system_practice.clean_architecture_1.domain.repository.ArtistRepository

class ArtistRepositoryImpl(
    private val artistRemoteDataSource: ArtistRemoteDataSource,
    private val artistLocalDataSource: ArtistLocalDataSource,
    private val artistCacheDataSource: ArtistCacheDataSource
) : ArtistRepository {
    override suspend fun getArtists(): List<Artist>? {
        return getArtistListFromCache()
    }

    override suspend fun updateArtists(): List<Artist>? {
        artistLocalDataSource.clearAll()
        val artists = artistRemoteDataSource.getArtists().body()?.results
        artists?.let {
            artistLocalDataSource.saveArtists(artists)
            artistCacheDataSource.updateArtistToCache(artists)
        }

        return artists
    }

    private suspend fun getArtistListFromCache(): List<Artist> {
        lateinit var artists: List<Artist>

        try {
            artists = artistCacheDataSource.getArtistFromCache()
            if (artists.isEmpty()) {
                artists = getArtistListFromDb()
            }
        } catch (e: Exception) {
            Log.e("ArtistRepositoryImpl", e.message.toString())
        }

        return artists
    }

    private suspend fun getArtistListFromDb(): List<Artist> {
        lateinit var artists: List<Artist>

        try {
            artists = artistLocalDataSource.getArtists()
            if (artists.isEmpty()) {
                artists = getArtistListFromNetwork()
            }
        } catch (e: Exception) {
            Log.e("ArtistRepositoryImpl", e.message.toString())
        }

        return artists
    }

    private suspend fun getArtistListFromNetwork(): List<Artist> {
        var artists: List<Artist> = emptyList()

        try {
            val response = artistRemoteDataSource.getArtists()
            if (response.body() != null) artists = response.body()!!.results
        } catch (e: Exception) {
            Log.e("ArtistRepositoryImpl", e.message.toString())
        }

        return artists
    }


}