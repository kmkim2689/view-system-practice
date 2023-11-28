package com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.data_source_impl

import com.practice.view_system_practice.clean_architecture_1.data.model.tv_show.TvShowList
import com.practice.view_system_practice.clean_architecture_1.data.remote.TMDBService
import com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.data_source.TvShowRemoteDataSource
import retrofit2.Response

class TvShowRemoteDataSourceImpl(
    private val api: TMDBService,
    private val apiKey: String
) : TvShowRemoteDataSource {
    override suspend fun getPopularTvShows(): Response<TvShowList> {
        return api.getPopularTvShows(apiKey)
    }
}