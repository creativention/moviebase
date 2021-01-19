package com.kadon.moviebase.core.domain.usecase

import com.kadon.moviebase.core.data.Resource
import com.kadon.moviebase.core.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getMovies(s: String, page: Int): Flow<Resource<List<MovieModel>>>
    fun getFavoriteMovies(): Flow<List<MovieModel>>
    fun setFavoriteMovie(movieModel: MovieModel, isFavorite: Boolean)
}