package com.kadon.moviebase.core.domain.usecase

import androidx.paging.PagingData
import com.kadon.moviebase.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviePagingUseCase {
    fun getPagedMovie() : Flow<PagingData<Movie>>
}