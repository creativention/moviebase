package com.kadon.moviebase.core.data.source.local

import com.kadon.moviebase.core.data.source.local.entity.MovieEntity
import com.kadon.moviebase.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class LocalDataSource(
    private val movieDao: MovieDao
) {
    fun getMovies(): Flow<List<MovieEntity>> = movieDao.getMovies()
    fun getFavoriteMovies(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovies()
    suspend fun insertMovies(movies: List<MovieEntity>) = movieDao.insertMovies(movies)

    suspend fun saveFavoriteMovie(movie: MovieEntity, isFavorite: Boolean): Int {
        movie.isFavorite = isFavorite
        return movieDao.updateFaveMovie(movie)
    }

    fun getMovieDetail(movieId: Long): Flow<MovieEntity> {
        Timber.d("Reach this function movieId = $movieId")
        return movieDao.getMovieDetail(movieId)
    }
}