package com.kadon.moviebase.core.data.source.local

import androidx.paging.PagingSource
import com.kadon.moviebase.core.data.source.local.entity.FavoriteEntity
import com.kadon.moviebase.core.data.source.local.entity.MovieEntity
import com.kadon.moviebase.core.data.source.local.entity.RemoteKeys
import com.kadon.moviebase.core.data.source.local.room.FavoriteDao
import com.kadon.moviebase.core.data.source.local.room.MovieDao
import com.kadon.moviebase.core.data.source.local.room.RemoteKeysDao
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class LocalDataSource(
    private val movieDao: MovieDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val favoriteDao: FavoriteDao,
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

    fun getPagingMovies(): PagingSource<Int, MovieEntity> = movieDao.getPagingMovies()

    suspend fun clearMovies() = movieDao.clearMovie()
    suspend fun clearRemoteKeys() {
        Timber.d("MediatorResult -> clearRemoteKeys")
        remoteKeysDao.clearRemoteKeys()
    }

    suspend fun insertRemoteKeys(keys: List<RemoteKeys>) {
        remoteKeysDao.insertAll(keys)
    }

    suspend fun remoteKeysRepoId(movieId: Long): RemoteKeys? {
        return remoteKeysDao.remoteKeysRepoId(movieId)
    }

    suspend fun insertFavorite(vararg data: FavoriteEntity) = favoriteDao.insertFavorite(*data)
    suspend fun updateFavorite(data: FavoriteEntity, isFavorite: Boolean) {
        data.isFavorite = isFavorite
        favoriteDao.updateFavorite(data)
    }

    fun getPagingFavorite(): PagingSource<Int, FavoriteEntity> = favoriteDao.getPagingFavorite()

    fun isFavorite(movieId: Long): Flow<Boolean> {
        return favoriteDao.isMovieFavorite(movieId)
    }

    fun getFavoriteMovieDetail(movieId: Long): Flow<FavoriteEntity> {
        return favoriteDao.getFavoriteMovieDetail(movieId)
    }
}