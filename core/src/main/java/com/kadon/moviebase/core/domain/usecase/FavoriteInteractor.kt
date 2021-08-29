package com.kadon.moviebase.core.domain.usecase

import androidx.paging.PagingData
import com.kadon.moviebase.core.domain.model.Movie
import com.kadon.moviebase.core.domain.repository.IFavoriteRespository
import kotlinx.coroutines.flow.Flow

class FavoriteInteractor(private val repository: IFavoriteRespository) : FavoriteUseCase{
    override fun getPagingFavorite(): Flow<PagingData<Movie>> {
        return repository.getPagingFavorite()
    }

    override suspend fun insertFavorite(movie: Movie) {
        repository.insertFavorite(movie)
    }

    override suspend fun updateFavorite(movie: Movie, isFavorite: Boolean) {
        repository.updateFavorite(movie, isFavorite)
    }

    override fun isFavorite(movieId: Long): Flow<Boolean?> {
        return repository.isFavorite(movieId)
    }

    override fun getMovieDetail(movieId: Long): Flow<Movie> {
        return repository.getMovieDetail(movieId)
    }
}