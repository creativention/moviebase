package com.kadon.moviebase.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val movieId: Long,
    val overview: String,
    val originalLanguage: String,
    val originalTitle: String,
    val video: Boolean,
    var movieTitle: String,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val releaseDate: String? = null,
    val popularity: Double,
    val voteAverage: Double,
    val adult: Boolean,
    val voteCount: Int,
    var isFavorite: Boolean? = false,
) : Parcelable
