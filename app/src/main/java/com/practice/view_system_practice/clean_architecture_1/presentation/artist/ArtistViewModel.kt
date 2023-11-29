package com.practice.view_system_practice.clean_architecture_1.presentation.artist

import androidx.lifecycle.liveData
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.GetArtistsUseCase
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.UpdateArtistsUseCase

class ArtistViewModel(
    private val getArtistsUseCase: GetArtistsUseCase,
    private val updateArtistsUseCase: UpdateArtistsUseCase
) {

    fun getArtists() = liveData {
        val artistList = getArtistsUseCase.execute()
        emit(artistList)
    }

    fun updateArtists() = liveData {
        val updatedArtistList = updateArtistsUseCase.execute()
        emit(updatedArtistList)
    }

}