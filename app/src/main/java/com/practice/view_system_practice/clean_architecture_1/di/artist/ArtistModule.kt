package com.practice.view_system_practice.clean_architecture_1.di.artist

import com.practice.view_system_practice.clean_architecture_1.domain.use_case.GetArtistsUseCase
import com.practice.view_system_practice.clean_architecture_1.domain.use_case.UpdateArtistsUseCase
import com.practice.view_system_practice.clean_architecture_1.presentation.artist.ArtistViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ArtistModule {

    @ArtistScope
    @Provides
    fun provideArtistViewModelFactory(
        getArtistsUseCase: GetArtistsUseCase,
        updateArtistsUseCase: UpdateArtistsUseCase
    ): ArtistViewModelFactory = ArtistViewModelFactory(
        getArtistsUseCase, updateArtistsUseCase
    )
}