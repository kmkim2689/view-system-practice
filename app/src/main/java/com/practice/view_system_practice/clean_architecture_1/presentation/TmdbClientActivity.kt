package com.practice.view_system_practice.clean_architecture_1.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.practice.view_system_practice.R
import com.practice.view_system_practice.clean_architecture_1.presentation.artist.ArtistActivity
import com.practice.view_system_practice.clean_architecture_1.presentation.movie.MovieActivity
import com.practice.view_system_practice.clean_architecture_1.presentation.tv_show.TvShowActivity
import com.practice.view_system_practice.databinding.ActivityTmdbClientBinding

class TmdbClientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTmdbClientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tmdb_client)

        binding.apply {
            btnMovie.setOnClickListener {
                val intent = Intent(this@TmdbClientActivity, MovieActivity::class.java)
                startActivity(intent)
            }

            btnTv.setOnClickListener {
                val intent = Intent(this@TmdbClientActivity, TvShowActivity::class.java)
                startActivity(intent)
            }

            btnActor.setOnClickListener {
                val intent = Intent(this@TmdbClientActivity, ArtistActivity::class.java)
                startActivity(intent)
            }
        }
    }
}