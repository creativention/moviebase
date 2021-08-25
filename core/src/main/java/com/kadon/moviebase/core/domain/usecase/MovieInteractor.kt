package com.kadon.moviebase.core.domain.usecase

import com.kadon.moviebase.core.data.Resource
import com.kadon.moviebase.core.domain.model.Movie
import com.kadon.moviebase.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

@Suppress("SpellCheckingInspection")
class MovieInteractor(
    private val movieRepository: IMovieRepository
) : MovieUseCase {
    override fun getMovies(s: String, page: Int): Flow<Resource<List<Movie>>> {
        return movieRepository.getMovies(s, page)
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return movieRepository.getFavoriteMovies()
    }

    override fun setFavoriteMovie(movie: Movie, isFavorite: Boolean) : Flow<Int> {
        return movieRepository.setFavoriteMovie(movie, isFavorite)
    }

    override fun getMovieDetail(movieId: Long): Flow<Movie> {
        return movieRepository.getMovieDetail(movieId)
    }
}