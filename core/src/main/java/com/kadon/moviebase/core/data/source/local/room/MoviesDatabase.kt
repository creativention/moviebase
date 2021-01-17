package com.kadon.moviebase.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kadon.moviebase.core.data.source.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}