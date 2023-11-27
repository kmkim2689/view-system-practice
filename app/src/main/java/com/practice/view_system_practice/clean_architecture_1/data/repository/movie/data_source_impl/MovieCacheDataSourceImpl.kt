package com.practice.view_system_practice.clean_architecture_1.data.repository.movie.data_source_impl

import com.practice.view_system_practice.clean_architecture_1.data.model.movie.Movie
import com.practice.view_system_practice.clean_architecture_1.data.repository.movie.data_source.MovieCacheDataSource

class MovieCacheDataSourceImpl : MovieCacheDataSource {
    // 기초적인 캐시 매커니즘 활용
    // 여기서는, ArrayList를 활용
    // 유저가 처음으로 앱에 접속하여 데이터를 불러올 때, api로부터 데이터를 불러와 이 arraylist에 적재
    // 이 캐시 데이터 소스는 싱글톤으로서 존재하며, arraylist가 영화에 대한 데이터를 담게 된다.
    // 따라서, 유저가 다시 영화의 리스트를 불러오고자 할 때(재접속), 데이터베이스를 사용하는 것이 아니라 이 cache data source 인스턴스를 활용

    private var movieList = ArrayList<Movie>()

    override suspend fun getMoviesFromCache(): List<Movie> {
        return movieList
    }

    override suspend fun saveMoviesToCache(movies: List<Movie>) {
        movieList.clear()
        movieList = ArrayList(movies)
    }
}