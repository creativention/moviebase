package com.kadon.moviebase.di

import com.kadon.moviebase.core.domain.usecase.MovieInteractor
import com.kadon.moviebase.core.domain.usecase.MovieUseCase
import com.kadon.moviebase.ui.detail.DetailViewModel
import com.kadon.moviebase.ui.favorite.FavoriteViewModel
import com.kadon.moviebase.ui.movie.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> {
        MovieInteractor(get())
    }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}