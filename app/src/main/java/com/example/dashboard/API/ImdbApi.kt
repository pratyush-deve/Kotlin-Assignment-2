package com.example.dashboard.API

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImdbApi {
    @GET("api/imdb/search")
    suspend fun searchMovies(
        @Query("originalTitle") query: String,
        @Query("type") type: String = "movie",
        @Query("rows") rows: Int = 25
    ): Response<ImdbSearchResponse>

    @GET("title/details")
    suspend fun getMovieDetails(
        @Query("id") imdbId: String
    ): Response<ImdbMovie>

}
