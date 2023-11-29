package com.practice.view_system_practice.clean_architecture_1.presentation.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.view_system_practice.R
import com.practice.view_system_practice.clean_architecture_1.di.Injector
import com.practice.view_system_practice.databinding.ActivityMovieBinding
import javax.inject.Inject

class MovieActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: MovieViewModelFactory
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var binding: ActivityMovieBinding
    private lateinit var adapter: MovieRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)

        (application as Injector).createMovieSubComponent()
            .inject(this)

        movieViewModel = ViewModelProvider(this, viewModelFactory)
            .get(MovieViewModel::class.java)

        initRecyclerView()

    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        adapter = MovieRVAdapter()
        binding.rvMovie.layoutManager = layoutManager
        binding.rvMovie.adapter = adapter
    }

    private fun displayMovies() {
        binding.progressBarMovie.visibility = View.VISIBLE
        val responseLiveData = movieViewModel.getMovies()
        responseLiveData.observe(this) {
            Log.i("MovieAcivity", "$it")
            if (it != null) {
                adapter.setList(it)
                adapter.notifyDataSetChanged()
                binding.progressBarMovie.visibility = View.GONE
            } else {
                binding.progressBarMovie.visibility = View.GONE
                Toast.makeText(applicationContext, "no data available", LENGTH_SHORT)
            }
        }

    }

    // 앱바에 직접적으로 메뉴를 구현하는 방법
    // 1. menu 리소스 파일을 만든다. menu 태그 하위에 item을 만들어, id를 부여하고 아이콘,타이틀 등을 부여한다.
    // 2. 액티비티/프래그먼트에서 두 개의 함수를 오버라이드한다.
    // 2.1. oncreateoptionsmenu
    // 2.2. onoptionsitemselected

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.update, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_update -> {
                updateMovies()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun updateMovies() {
        binding.progressBarMovie.visibility = View.VISIBLE
        val response = movieViewModel.getMovies()
        response.observe(this) {
            if (it != null) {
                adapter.setList(it)
                adapter.notifyDataSetChanged()
                binding.progressBarMovie.visibility = View.GONE
            } else {
                binding.progressBarMovie.visibility = View.GONE
                Toast.makeText(applicationContext, "no data available", LENGTH_SHORT)
            }
        }
    }
}