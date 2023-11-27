package com.practice.view_system_practice.clean_architecture_1.data.repository.movie.data_source_impl

import com.practice.view_system_practice.clean_architecture_1.data.local.MovieDao
import com.practice.view_system_practice.clean_architecture_1.data.model.movie.Movie
import com.practice.view_system_practice.clean_architecture_1.data.repository.movie.data_source.MovieLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieLocalDataSourceImpl(
    private val dao: MovieDao
) : MovieLocalDataSource {
    // room으로부터 데이터를 get할 때에는, room 라이브러리가 백그라운드 스레드에서 쿼리를 수행한다.
    // 따라서 백그라운드 로직을 위한 별도의 코드가 필요 없음
    override suspend fun getMoviesFromDb(): List<Movie> = dao.getMovies()

    // 다만 다른 쿼리들은 백그라운드 스레드에서 수행하도록 조치를 취해야함
    override suspend fun saveMoviesToDB(movies: List<Movie>) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.saveMovies(movies)
        }
    }

    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteAllMovies()
        }
    }
}