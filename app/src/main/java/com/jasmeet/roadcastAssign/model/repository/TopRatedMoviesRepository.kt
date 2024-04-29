package com.jasmeet.roadcastAssign.model.repository

import com.jasmeet.roadcastAssign.model.topRated.TopRatedMoviesResponse

interface TopRatedMoviesRepository {
    suspend fun getTopRatedMovies(page: Int): TopRatedMoviesResponse
}