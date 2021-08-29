package com.kadon.moviebase.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kadon.moviebase.core.domain.model.Movie
import com.kadon.moviebase.core.domain.usecase.FavoriteUseCase
import com.kadon.moviebase.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class DetailViewModel(
    private val movieUseCase: MovieUseCase,
    private val favoriteUseCase: FavoriteUseCase
) : ViewModel() {
    private var _movie = MutableLiveData<Movie>()
    val movie : LiveData<Movie> get() = _movie

    fun getMovieDetail(movieId: Long, from: String?) {
        viewModelScope.launch {
            val isFave = favoriteUseCase.isFavorite(movieId)

            val mov = if (from.isNullOrEmpty()) movieUseCase.getMovieDetail(movieId) else
                favoriteUseCase.getMovieDetail(movieId)

            mov.zip(isFave){ m, b ->
                m.isFavorite = b?:false
                m
            }.collect {
                _movie.value = it
            }
        }
    }

    fun insertMovie(movie: Movie) {
        viewModelScope.launch {  favoriteUseCase.insertFavorite(movie) }
    }
}