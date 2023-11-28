package com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.data_source

import com.practice.view_system_practice.clean_architecture_1.data.model.tv_show.TvShow
import com.practice.view_system_practice.clean_architecture_1.data.model.tv_show.TvShowList
import retrofit2.Response

interface TvShowRemoteDataSource {
    suspend fun getPopularTvShows(): Response<TvShowList>
}