package com.practice.view_system_practice.clean_architecture_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.practice.view_system_practice.R

class TmdbClientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tmdb_client)
    }
}