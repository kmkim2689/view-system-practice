package com.practice.view_system_practice.clean_architecture_1.di.core

import android.content.Context
import com.practice.view_system_practice.clean_architecture_1.di.artist.ArtistSubComponent
import com.practice.view_system_practice.clean_architecture_1.di.movie.MovieSubComponent
import com.practice.view_system_practice.clean_architecture_1.di.tv_show.TvShowSubComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = [
    MovieSubComponent::class,
    TvShowSubComponent::class,
    ArtistSubComponent::class
])
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideAppplicationContext(): Context = context.applicationContext
}