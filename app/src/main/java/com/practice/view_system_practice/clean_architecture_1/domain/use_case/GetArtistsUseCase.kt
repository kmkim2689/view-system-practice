package com.practice.view_system_practice.clean_architecture_1.domain.use_case

import com.practice.view_system_practice.clean_architecture_1.data.model.artist.Artist
import com.practice.view_system_practice.clean_architecture_1.domain.repository.ArtistRepository

class GetArtistsUseCase(
    private val artistRepository: ArtistRepository
) {

    suspend fun execute(): List<Artist>? = artistRepository.getArtists()

}