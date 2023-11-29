package com.practice.view_system_practice.clean_architecture_1.presentation.artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.GetArtistsUseCase
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.UpdateArtistsUseCase

class ArtistViewModelFactory(
    private val getArtistsUseCase: GetArtistsUseCase,
    private val updateArtistsUseCase: UpdateArtistsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArtistViewModel::class.java)) {
            return ArtistViewModel(getArtistsUseCase, updateArtistsUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}