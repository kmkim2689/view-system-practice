package com.practice.view_system_practice.clean_architecture_1.di.artist

import com.practice.view_system_practice.clean_architecture_1.presentation.artist.ArtistActivity
import dagger.Subcomponent

@ArtistScope
// use subcomponent to inject dependencies to ArtistActivity(특정 생명주기에서만 공급하기 위함)
@Subcomponent(modules = [ArtistModule::class])
interface ArtistSubComponent {

    fun inject(artistActivity: ArtistActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ArtistSubComponent
    }

}