package com.kadon.moviebase.core.domain.repository

import com.kadon.moviebase.core.data.Resource
import com.kadon.moviebase.core.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovies(s: String, page: Int): Flow<Resource<List<MovieModel>>>
    fun getFavoriteMovies(): Flow<List<MovieModel>>
    fun setFavoriteMovie(movieModel: MovieModel, isFavorite: Boolean): Flow<Int>
    fun getMovieDetail(movieId: Long): Flow<MovieModel>
}