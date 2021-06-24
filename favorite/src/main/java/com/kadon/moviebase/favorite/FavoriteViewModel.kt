package com.kadon.moviebase.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kadon.moviebase.core.domain.usecase.MovieUseCase

class FavoriteViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val favoriteMovie = movieUseCase.getFavoriteMovies().asLiveData()
}