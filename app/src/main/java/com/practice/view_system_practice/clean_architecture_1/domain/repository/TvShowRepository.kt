package com.practice.view_system_practice.clean_architecture_1.domain.repository

import com.practice.view_system_practice.clean_architecture_1.data.model.tv_show.TvShow

interface TvShowRepository {

    suspend fun getTvShows(): List<TvShow>?

    suspend fun updateTvShows(): List<TvShow>?

}