package com.kadon.moviebase.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(
    val movieId: Int,
    val overview: String,
    val originalLanguage: String,
    val originalTitle: String,
    val video: Boolean,
    var movieTitle: String,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val releaseDate: String,
    val popularity: Double,
    val voteAverage: Double,
    val adult: Boolean,
    val voteCount: Int,
    val isFavorite: Boolean
) : Parcelable
