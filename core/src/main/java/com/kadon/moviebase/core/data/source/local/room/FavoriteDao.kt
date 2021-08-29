package com.kadon.moviebase.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.*
import com.kadon.moviebase.core.data.source.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(vararg favoriteEntity: FavoriteEntity)

    @Update
    suspend fun updateFavorite(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite WHERE isFavorite = 1")
    fun getPagingFavorite(): PagingSource<Int, FavoriteEntity>

    @Query("SELECT isFavorite FROM favorite WHERE movieId = :movieId")
    fun isMovieFavorite(movieId: Long): Flow<Boolean>

    @Query("SELECT * FROM favorite WHERE movieId = :movieId")
    fun getFavoriteMovieDetail(movieId: Long): Flow<FavoriteEntity>
}