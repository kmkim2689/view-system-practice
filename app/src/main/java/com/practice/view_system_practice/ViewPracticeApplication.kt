package com.practice.view_system_practice

import android.app.Application
import com.practice.view_system_practice.clean_architecture_1.di.Injector
import com.practice.view_system_practice.clean_architecture_1.di.artist.ArtistSubComponent
import com.practice.view_system_practice.clean_architecture_1.di.core.AppComponent
import com.practice.view_system_practice.clean_architecture_1.di.core.AppModule
import com.practice.view_system_practice.clean_architecture_1.di.core.DaggerAppComponent
import com.practice.view_system_practice.clean_architecture_1.di.core.NetworkModule
import com.practice.view_system_practice.clean_architecture_1.di.core.RemoteDataModule
import com.practice.view_system_practice.clean_architecture_1.di.movie.MovieSubComponent
import com.practice.view_system_practice.clean_architecture_1.di.tv_show.TvShowSubComponent
import com.practice.view_system_practice.dagger.DaggerSmartPhoneComponent
import com.practice.view_system_practice.dagger.MemoryCardModule
import com.practice.view_system_practice.dagger.SmartPhoneComponent

class ViewPracticeApplication : Application(), Injector {

    lateinit var smartPhoneComponent: SmartPhoneComponent
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        smartPhoneComponent = initDagger()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .networkModule(NetworkModule(BuildConfig.BASE_URL))
            .remoteDataModule(RemoteDataModule(BuildConfig.API_KEY))
            .build()
    }

    override fun createMovieSubComponent(): MovieSubComponent {
        return appComponent.movieSubComponent().create()
    }

    override fun createTvShowSubComponent(): TvShowSubComponent {
        return appComponent.tvShowSubComponent().create()
    }

    override fun createArtistSubComponent(): ArtistSubComponent {
        return appComponent.artistSubcomponent().create()
    }



    private fun initDagger(): SmartPhoneComponent = DaggerSmartPhoneComponent.builder()
        .memoryCardModule(MemoryCardModule(1000))
        .build() // build까지만 진행
}