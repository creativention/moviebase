package com.kadon.moviebase.core.data

import androidx.paging.*
import com.kadon.moviebase.core.data.source.local.LocalDataSource
import com.kadon.moviebase.core.domain.model.Movie
import com.kadon.moviebase.core.domain.repository.IMoviePagingRepository
import com.kadon.moviebase.core.utils.MapData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ExperimentalPagingApi
class MoviePagingRepository(
    private val localDataSource: LocalDataSource,
    private val remoteMediator: MovieRemoteMediator,
) : IMoviePagingRepository {

    override fun getPagedMovie(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(20),
            remoteMediator = remoteMediator
        ) {
            localDataSource.getPagingMovies()
        }.flow.map {
            it.map { m ->
                MapData.mapMovieEntityToDomain(m)
            }
        }
    }
}