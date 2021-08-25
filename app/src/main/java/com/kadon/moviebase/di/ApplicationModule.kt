package com.kadon.moviebase.di

import com.kadon.moviebase.core.domain.usecase.MovieInteractor
import com.kadon.moviebase.core.domain.usecase.MoviePagingInteractor
import com.kadon.moviebase.core.domain.usecase.MoviePagingUseCase
import com.kadon.moviebase.core.domain.usecase.MovieUseCase
import com.kadon.moviebase.ui.detail.DetailViewModel
import com.kadon.moviebase.ui.movie.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
    factory<MoviePagingUseCase> { MoviePagingInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}