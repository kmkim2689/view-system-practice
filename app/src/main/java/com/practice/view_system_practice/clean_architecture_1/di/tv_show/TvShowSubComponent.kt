package com.practice.view_system_practice.clean_architecture_1.di.tv_show

import com.practice.view_system_practice.clean_architecture_1.presentation.tv_show.TvShowActivity
import dagger.Subcomponent

@TvShowScope
// use subcomponent to inject dependencies to ArtistActivity(특정 생명주기에서만 공급하기 위함)
@Subcomponent(modules = [TvShowModule::class])
interface TvShowSubComponent {

    fun inject(tvShowActivity: TvShowActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): TvShowSubComponent
    }

}