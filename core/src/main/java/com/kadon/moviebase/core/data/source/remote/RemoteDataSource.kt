package com.kadon.moviebase.core.data.source.remote

import android.util.Log
import com.kadon.moviebase.core.data.source.remote.api.ApiResponse
import com.kadon.moviebase.core.data.source.remote.api.ApiService
import com.kadon.moviebase.core.data.source.remote.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(
    private val apiService: ApiService
) {
    suspend fun getMovies(
        movieCategory: String,
        page: Int
    ): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.getMovies(category = movieCategory, page = page)
                val movieList = response.results
                if (movieList.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}