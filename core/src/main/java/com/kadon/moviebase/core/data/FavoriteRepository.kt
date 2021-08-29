package com.kadon.moviebase.core.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.kadon.moviebase.core.data.source.local.LocalDataSource
import com.kadon.moviebase.core.data.source.local.entity.FavoriteEntity
import com.kadon.moviebase.core.domain.model.Movie
import com.kadon.moviebase.core.domain.repository.IFavoriteRespository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepository(private val localDataSource: LocalDataSource) : IFavoriteRespository {
    override fun getPagingFavorite(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 10)
        ) {
            localDataSource.getPagingFavorite()
        }.flow.map { f ->
            f.map {
                Movie(
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
        }
    }

    override suspend fun insertFavorite(movie: Movie) {
        val data = movie.let {
            FavoriteEntity(
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
        localDataSource.insertFavorite(data)
    }

    override suspend fun updateFavorite(movie: Movie, isFavorite: Boolean) {
        val data = movie.let {
            FavoriteEntity(
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

        localDataSource.updateFavorite(data, isFavorite)
    }

    override fun isFavorite(movieId: Long): Flow<Boolean> {
        return localDataSource.isFavorite(movieId)
    }

    override fun getMovieDetail(movieId: Long): Flow<Movie> {
        return localDataSource.getFavoriteMovieDetail(movieId).map {
            Movie(
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
    }
}