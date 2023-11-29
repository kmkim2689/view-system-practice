package com.practice.view_system_practice.clean_architecture_1.presentation.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.practice.view_system_practice.R
import com.practice.view_system_practice.clean_architecture_1.di.Injector
import com.practice.view_system_practice.databinding.ActivityMovieBinding
import javax.inject.Inject

class MovieActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: MovieViewModelFactory
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var binding: ActivityMovieBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)

        (application as Injector).createMovieSubComponent()
            .inject(this)

        movieViewModel = ViewModelProvider(this, viewModelFactory)
            .get(MovieViewModel::class.java)

        val responseLiveData = movieViewModel.getMovies()
        responseLiveData.observe(this) {
            Log.i("MovieAcivity", "$it")
        }
    }
}