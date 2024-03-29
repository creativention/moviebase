package com.kadon.moviebase.core.utils

import com.kadon.moviebase.core.data.source.local.entity.MovieEntity
import com.kadon.moviebase.core.data.source.remote.response.MovieResponse
import com.kadon.moviebase.core.domain.model.MovieModel

object MapData {
    fun mapMovieResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        return input.map {
            MovieEntity(
                movieId = it.id,
                overview = it.overview,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                video = it.video,
                movieTitle = it.title,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                voteAverage = it.voteAverage,
                adult = it.adult,
                voteCount = it.voteCount,
                isFavorite = false
            )
        }
    }

    fun mapMovieEntitiesToDomain(input: List<MovieEntity>): List<MovieModel> {
        return input.map {
            MovieModel(
                movieId = it.movieId,
                overview = it.overview,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                video = it.video,
                movieTitle = it.movieTitle,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                voteAverage = it.voteAverage,
                adult = it.adult,
                voteCount = it.voteCount,
                isFavorite = it.isFavorite
            )
        }
    }

    fun mapDomainToMovieEntities(input: MovieModel) = MovieEntity(
        movieId = input.movieId,
        overview = input.overview,
        originalLanguage = input.originalLanguage,
        originalTitle = input.originalTitle,
        video = input.video,
        movieTitle = input.movieTitle,
        posterPath = input.posterPath,
        backdropPath = input.backdropPath,
        releaseDate = input.releaseDate,
        popularity = input.popularity,
        voteAverage = input.voteAverage,
        adult = input.adult,
        voteCount = input.voteCount,
        isFavorite = input.isFavorite
    )

    fun mapMovieEntityToDomain(it: MovieEntity) : MovieModel =
        MovieModel(
             movieId = it.movieId,
             overview = it.overview,
             originalLanguage = it.originalLanguage,
             originalTitle = it.originalTitle,
             video = it.video,
             movieTitle = it.movieTitle,
             posterPath = it.posterPath,
             backdropPath = it.backdropPath,
             releaseDate = it.releaseDate,
             popularity = it.popularity,
             voteAverage = it.voteAverage,
             adult = it.adult,
             voteCount = it.voteCount,
             isFavorite = it.isFavorite,
        )
}