package com.kadon.moviebase.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kadon.moviebase.core.domain.usecase.MoviePagingUseCase

class MovieViewModel(moviePagingUseCase: MoviePagingUseCase) : ViewModel() {
    //var movieLiveData = movieUseCase.getMovies(K.CATEGORY_POPULAR, K.PAGE).asLiveData()
    val movies = moviePagingUseCase.getPagedMovie().cachedIn(viewModelScope)
}