package com.kadon.moviebase.core.data.source.remote.api

import com.kadon.moviebase.core.data.source.remote.response.GetMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/3/movie/{category}")
    suspend fun getMovies(
            @Path("category")
            category: String,

            @Query("api_key")
            apiKey: String,

            @Query("language")
            language: String,

            @Query("page")
            page: Int
    ): GetMovieResponse
}