package com.kadon.moviebase.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kadon.moviebase.core.domain.usecase.MovieUseCase
import com.kadon.moviebase.core.utils.K

class MovieViewModel(movieUseCase: MovieUseCase) : ViewModel() {

    var movieLiveData = movieUseCase.getMovies(K.CATEGORY_POPULAR, K.PAGE).asLiveData()
}