package com.jasmeet.roadcastAssign.model.apiService

import com.jasmeet.roadcastAssign.model.topRated.TopRatedMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "3104c51d30485a8cb179ccb6ae227ee1"
    }

    @GET("movie/top_rated?api_key=$API_KEY")
    suspend fun getTopRatedMovies(@Query("page") page: Int): TopRatedMoviesResponse
}