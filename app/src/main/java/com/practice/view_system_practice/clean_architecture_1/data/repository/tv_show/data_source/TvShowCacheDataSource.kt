package com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.data_source

import com.practice.view_system_practice.clean_architecture_1.data.model.tv_show.TvShow

interface TvShowCacheDataSource {
    suspend fun getTvShowsFromCache(): List<TvShow>

    suspend fun saveTvShowsToCache(tvShows: List<TvShow>)
}