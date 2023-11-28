package com.practice.view_system_practice.clean_architecture_1.data.repository.artist.data_source

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practice.view_system_practice.clean_architecture_1.data.model.artist.Artist

interface ArtistLocalDataSource {
    suspend fun saveArtists(artists: List<Artist>)

    suspend fun clearAll()

    suspend fun getArtists(): List<Artist>
}