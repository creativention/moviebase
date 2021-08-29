package com.kadon.moviebase.core.domain.usecase

import androidx.paging.PagingData
import com.kadon.moviebase.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface FavoriteUseCase {
    fun getPagingFavorite(): Flow<PagingData<Movie>>
    suspend fun insertFavorite(movie: Movie)
    suspend fun updateFavorite(movie: Movie, isFavorite: Boolean)
    fun isFavorite(movieId: Long): Flow<Boolean?>
    fun getMovieDetail(movieId: Long): Flow<Movie>
}