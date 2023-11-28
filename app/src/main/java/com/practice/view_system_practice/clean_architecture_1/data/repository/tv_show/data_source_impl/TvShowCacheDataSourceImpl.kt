package com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.data_source_impl

import com.practice.view_system_practice.clean_architecture_1.data.model.tv_show.TvShow
import com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.data_source.TvShowCacheDataSource

class TvShowCacheDataSourceImpl : TvShowCacheDataSource {

    private var tvShowList = ArrayList<TvShow>()

    override suspend fun getTvShowsFromCache(): List<TvShow> {
        return tvShowList
    }

    override suspend fun saveTvShowsToCache(tvShows: List<TvShow>) {
        tvShowList.clear()
        tvShowList = ArrayList(tvShows)
    }
}