package com.kadon.moviebase.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kadon.moviebase.core.domain.usecase.MovieUseCase

class MovieViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val movieLiveData = movieUseCase.getMovies().asLiveData()
}