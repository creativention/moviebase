package com.kadon.moviebase.core.data.source.local.room

import androidx.room.*
import com.kadon.moviebase.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Update
    fun updateFaveMovie(movie: MovieEntity)

    @Query("SELECT * FROM movies WHERE movieId = :movieId")
    fun getMovieDetail(movieId: Long): Flow<MovieEntity>

}