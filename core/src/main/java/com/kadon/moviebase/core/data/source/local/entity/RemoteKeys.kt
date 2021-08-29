package com.kadon.moviebase.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val movieId: Long,
    val title: String?,
    val prevKey: Int?,
    val nextKey: Int?
)

