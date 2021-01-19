package com.kadon.moviebase.ui.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kadon.moviebase.core.data.Resource
import com.kadon.moviebase.core.domain.model.MovieModel
import com.kadon.moviebase.core.domain.usecase.MovieUseCase
import com.kadon.moviebase.core.utils.K

class MovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    private val _query = MutableLiveData<String>()
    var movieLiveData = movieUseCase.getMovies(K.CATEGORY_POPULAR, K.PAGE).asLiveData()

    fun loadMore(movieCategory: String, page: Int): LiveData<Resource<List<MovieModel>>>{
        return movieUseCase.getMovies(movieCategory, page).asLiveData()
    }
}