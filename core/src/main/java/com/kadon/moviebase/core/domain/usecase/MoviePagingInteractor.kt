package com.kadon.moviebase.core.domain.usecase

import androidx.paging.PagingData
import com.kadon.moviebase.core.domain.model.Movie
import com.kadon.moviebase.core.domain.repository.IMoviePagingRepository
import kotlinx.coroutines.flow.Flow

class MoviePagingInteractor(private val repository: IMoviePagingRepository) : MoviePagingUseCase {
    override fun getPagedMovie(): Flow<PagingData<Movie>> {
        return repository.getPagedMovie()
    }
}