package com.practice.view_system_practice.clean_architecture_1.presentation.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practice.view_system_practice.R
import com.practice.view_system_practice.clean_architecture_1.data.model.movie.Movie
import com.practice.view_system_practice.databinding.ItemMovieBinding

class MovieRVAdapter() : RecyclerView.Adapter<MovieRVAdapter.MovieViewHolder>() {

    private var movieList = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMovieBinding>(
            layoutInflater,
            R.layout.item_movie,
            parent,
            false
        )

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size

    fun setList(movies: List<Movie>) {
        movieList.clear()
        movieList.addAll(movies)
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.tvTitle.text = movie.title
            binding.tvDescription.text = movie.overview
            val posterUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
            Glide.with(binding.ivThumbnail.context)
                .load(posterUrl)
                .into(binding.ivThumbnail)
        }
    }
}