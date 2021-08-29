package com.kadon.moviebase.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kadon.moviebase.core.data.source.local.entity.FavoriteEntity
import com.kadon.moviebase.core.data.source.local.entity.MovieEntity
import com.kadon.moviebase.core.data.source.local.entity.RemoteKeys

@Database(
    entities = [MovieEntity::class, RemoteKeys::class, FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun favoriteDao(): FavoriteDao

}