package com.kadon.moviebase.core.data.source.remote.api

import com.kadon.moviebase.core.data.source.remote.response.GetMovieResponse
import com.kadon.moviebase.core.utils.K
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/3/movie/{category}")
    suspend fun getMovies(
            @Path("category")
            category: String,

            @Query("api_key")
            apiKey: String = K.API_KEY,

            @Query("language")
            language: String = K.LANGUAGE,

            @Query("page")
            page: Int
    ): GetMovieResponse

    @GET("/3/movie/{category}")
    suspend fun getMoviesWithPaging(
            @Path("category")
            category: String,

            @Query("api_key")
            apiKey: String = K.API_KEY,

            @Query("language")
            language: String = K.LANGUAGE,

            @Query("page")
            page: Int
    ): GetMovieResponse
}