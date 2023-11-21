package com.practice.view_system_practice.clean_architecture_1.domain.repository

import com.practice.view_system_practice.clean_architecture_1.data.model.artist.Artist
import com.practice.view_system_practice.clean_architecture_1.data.model.tv_show.TvShow

interface ArtistRepository {

    suspend fun getArtists(): List<Artist>?

    suspend fun updateArtists(): List<Artist>?

}