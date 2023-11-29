package com.practice.view_system_practice.clean_architecture_1.di.movie

import com.practice.view_system_practice.clean_architecture_1.presentation.artist.ArtistActivity
import com.practice.view_system_practice.clean_architecture_1.presentation.movie.MovieActivity
import dagger.Subcomponent

@MovieScope
// use subcomponent to inject dependencies to ArtistActivity(특정 생명주기에서만 공급하기 위함)
@Subcomponent(modules = [MovieModule::class])
interface MovieSubComponent {

    fun inject(movieActivity: MovieActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): MovieSubComponent
    }

}