package com.kadon.moviebase.core.domain.usecase

import com.kadon.moviebase.core.data.Resource
import com.kadon.moviebase.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getMovies(s: String, page: Int): Flow<Resource<List<Movie>>>
    fun getFavoriteMovies(): Flow<List<Movie>>
    fun setFavoriteMovie(movie: Movie, isFavorite: Boolean): Flow<Int>
    fun getMovieDetail(movieId: Long): Flow<Movie>
}