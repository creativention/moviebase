package com.kadon.moviebase.favorite.di

import com.kadon.moviebase.favorite.FavoriteViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModul = module {
    viewModel { FavoriteViewModel(get()) }
}