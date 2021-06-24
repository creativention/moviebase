package com.kadon.moviebase.core.data.source.local

import com.kadon.moviebase.core.data.source.local.entity.MovieEntity
import com.kadon.moviebase.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val movieDao: MovieDao
) {
    fun getMovies(): Flow<List<MovieEntity>> = movieDao.getMovies()
    fun getFavoriteMovies(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovies()
    suspend fun insertMovies(movies: List<MovieEntity>) = movieDao.insertMovies(movies)

    fun saveFavoriteMovie(movie: MovieEntity, isFavorite: Boolean) {
        movie.isFavorite = isFavorite
        movieDao.updateFaveMovie(movie)
    }
}