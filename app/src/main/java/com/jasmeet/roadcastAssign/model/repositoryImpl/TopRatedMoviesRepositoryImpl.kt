package com.jasmeet.roadcastAssign.model.repositoryImpl

import com.jasmeet.roadcastAssign.model.apiService.ApiService
import com.jasmeet.roadcastAssign.model.repository.TopRatedMoviesRepository
import com.jasmeet.roadcastAssign.model.topRated.TopRatedMoviesResponse

class TopRatedMoviesRepositoryImpl(private val apiService: ApiService) :
    TopRatedMoviesRepository {
    override suspend fun getTopRatedMovies(page: Int): TopRatedMoviesResponse {
        return apiService.getTopRatedMovies(page)
    }
}