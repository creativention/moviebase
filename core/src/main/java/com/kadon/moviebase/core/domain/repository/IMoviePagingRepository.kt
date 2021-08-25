package com.kadon.moviebase.core.domain.repository

import androidx.paging.PagingData
import com.kadon.moviebase.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMoviePagingRepository {
    fun getPagedMovie() : Flow<PagingData<Movie>>
}