package com.kadon.moviebase.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kadon.moviebase.core.domain.usecase.FavoriteUseCase

class FavoriteViewModel(favoriteUseCase: FavoriteUseCase) : ViewModel() {
    val favoriteMovie = favoriteUseCase.getPagingFavorite().cachedIn(viewModelScope).asLiveData()
}