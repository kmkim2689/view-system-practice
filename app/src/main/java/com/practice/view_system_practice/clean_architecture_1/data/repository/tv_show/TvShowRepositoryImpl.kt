package com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show

import android.util.Log
import com.practice.view_system_practice.clean_architecture_1.data.model.movie.Movie
import com.practice.view_system_practice.clean_architecture_1.data.model.tv_show.TvShow
import com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.data_source.TvShowCacheDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.data_source.TvShowLocalDataSource
import com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.data_source.TvShowRemoteDataSource
import com.practice.view_system_practice.clean_architecture_1.domain.repository.TvShowRepository

class TvShowRepositoryImpl(
    private val tvShowCacheDataSource: TvShowCacheDataSource,
    private val tvShowLocalDataSource: TvShowLocalDataSource,
    private val tvShowRemoteDataSource: TvShowRemoteDataSource
) : TvShowRepository {

    /*
    * cache data source <--> local data source <--> remote data source
    *                 없으면 가져옴          없으면 가져옴
     */
    override suspend fun getTvShows(): List<TvShow>? {
        return getTvShowsFromCache()
    }

    override suspend fun updateTvShows(): List<TvShow>? {
        val newTvShows = getTvShowsFromNetwork()
        tvShowLocalDataSource.clearAll()
        tvShowLocalDataSource.saveTvShowsToDb(newTvShows)
        tvShowCacheDataSource.saveTvShowsToCache(newTvShows)

        return newTvShows
    }

    private suspend fun getTvShowsFromCache(): List<TvShow> {
        lateinit var tvShows: List<TvShow>
        try {
            tvShows = tvShowCacheDataSource.getTvShowsFromCache()
            if (tvShows.isEmpty()) {
                tvShows = getTvShowsFromDb()
                tvShowCacheDataSource.saveTvShowsToCache(tvShows)
            }
        } catch (e: Exception) {
            Log.d("TvShowRepositoryImpl", e.message.toString())
        }

        return tvShows
    }

    private suspend fun getTvShowsFromDb(): List<TvShow> {
        lateinit var tvShows: List<TvShow>
        try {
            tvShows = tvShowLocalDataSource.getTvShowsFromDb()
            if (tvShows.isEmpty()) {
                tvShows = getTvShowsFromNetwork()
                tvShowLocalDataSource.saveTvShowsToDb(tvShows)
            }
        } catch (e: Exception) {
            Log.d("TvShowRepositoryImpl", e.message.toString())
        }

        return tvShows
    }

    private suspend fun getTvShowsFromNetwork(): List<TvShow> {
        var tvShows: List<TvShow> = emptyList()
        try {
            val response = tvShowRemoteDataSource.getPopularTvShows()
            if (response.body() != null) tvShows = response.body()!!.results
        } catch (e: Exception) {
            Log.d("TvShowRepositoryImpl", e.message.toString())
        }

        return tvShows
    }
}