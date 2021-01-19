package com.kadon.moviebase.core.domain.usecase

import com.kadon.moviebase.core.data.Resource
import com.kadon.moviebase.core.domain.model.MovieModel
import com.kadon.moviebase.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(
    private val movieRepository: IMovieRepository
): MovieUseCase {
    override fun getMovies(s: String, page: Int): Flow<Resource<List<MovieModel>>> {
        return movieRepository.getMovies(s, page)
    }

    override fun getFavoriteMovies(): Flow<List<MovieModel>> {
        return movieRepository.getFavoriteMovies()
    }

    override fun setFavoriteMovie(movieModel: MovieModel, isFavorite: Boolean) {
        return movieRepository.setFavoriteMovie(movieModel, isFavorite)
    }
}