package com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.data_source_impl

import com.practice.view_system_practice.clean_architecture_1.data.local.TvShowDao
import com.practice.view_system_practice.clean_architecture_1.data.model.tv_show.TvShow
import com.practice.view_system_practice.clean_architecture_1.data.repository.tv_show.data_source.TvShowLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TvShowLocalDataSourceImpl(
    private val dao: TvShowDao
) : TvShowLocalDataSource {
    override suspend fun saveTvShowsToDb(shows: List<TvShow>) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.saveTvShows(shows)
        }
    }

    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteAllTvShows()
        }
    }

    override suspend fun getTvShowsFromDb(): List<TvShow> {
        return dao.getTvShows()
    }
}