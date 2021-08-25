package com.kadon.moviebase.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kadon.moviebase.core.domain.model.Movie
import com.kadon.moviebase.core.domain.usecase.MovieUseCase

class DetailViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun setFavoriteMovie(movie: Movie, isFavorite: Boolean) : LiveData<Int> =
        movieUseCase.setFavoriteMovie(movie, isFavorite).asLiveData()

    fun getMovieDetail(movieId: Long) = movieUseCase.getMovieDetail(movieId).asLiveData()
}