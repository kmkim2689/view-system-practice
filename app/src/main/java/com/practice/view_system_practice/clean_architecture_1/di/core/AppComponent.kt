package com.practice.view_system_practice.clean_architecture_1.di.core

import com.practice.view_system_practice.clean_architecture_1.di.artist.ArtistSubComponent
import com.practice.view_system_practice.clean_architecture_1.di.movie.MovieSubComponent
import com.practice.view_system_practice.clean_architecture_1.di.tv_show.TvShowSubComponent
import dagger.Component
import javax.inject.Singleton

@Singleton // to reuse across the whole app
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        RemoteDataModule::class,
        LocalDataModule::class,
        CacheDataModule::class,
        RepositoryModule::class,
        UseCaseModule::class
    ]
)
interface AppComponent {
    // Add Factory Methods here
    // to get subcomponents
    fun movieSubComponent(): MovieSubComponent.Factory
    fun tvShowSubComponent(): TvShowSubComponent.Factory
    fun artistSubcomponent(): ArtistSubComponent.Factory
}