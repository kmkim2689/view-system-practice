package com.practice.view_system_practice.clean_architecture_1.domain.use_case

import com.practice.view_system_practice.clean_architecture_1.data.model.tv_show.TvShow
import com.practice.view_system_practice.clean_architecture_1.domain.repository.TvShowRepository

class GetTvShowsUseCase(
    private val tvShowRepository: TvShowRepository
) {

    suspend fun execute(): List<TvShow>? = tvShowRepository.getTvShows()
}