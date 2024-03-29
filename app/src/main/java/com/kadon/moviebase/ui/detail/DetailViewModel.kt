package com.kadon.moviebase.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kadon.moviebase.core.domain.model.MovieModel
import com.kadon.moviebase.core.domain.usecase.MovieUseCase

class DetailViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun setFavoriteMovie(movieModel: MovieModel, isFavorite: Boolean) =
        movieUseCase.setFavoriteMovie(movieModel, isFavorite)

    fun getMovieDetail(movieId: Long) = movieUseCase.getMovieDetail(movieId).asLiveData()
}