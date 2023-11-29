package com.practice.view_system_practice.clean_architecture_1.di

import com.practice.view_system_practice.clean_architecture_1.di.artist.ArtistSubComponent
import com.practice.view_system_practice.clean_architecture_1.di.movie.MovieSubComponent
import com.practice.view_system_practice.clean_architecture_1.di.tv_show.TvShowSubComponent

interface Injector {
    fun createMovieSubComponent(): MovieSubComponent
    fun createTvShowSubComponent(): TvShowSubComponent
    fun createArtistSubComponent(): ArtistSubComponent
}