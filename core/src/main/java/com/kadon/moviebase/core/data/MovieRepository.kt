package com.kadon.moviebase.core.data

import com.kadon.moviebase.core.data.source.local.LocalDataSource
import com.kadon.moviebase.core.data.source.remote.RemoteDataSource
import com.kadon.moviebase.core.data.source.remote.api.ApiResponse
import com.kadon.moviebase.core.data.source.remote.response.MovieResponse
import com.kadon.moviebase.core.domain.model.MovieModel
import com.kadon.moviebase.core.domain.repository.IMovieRepository
import com.kadon.moviebase.core.utils.AppExecutors
import com.kadon.moviebase.core.utils.MapData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {
    override fun getMovies(
        s: String,
        page: Int
    ): Flow<Resource<List<MovieModel>>> {
        return object : NetworkBoundResource<List<MovieModel>, List<MovieResponse>>() {

            override fun loadFromDB(): Flow<List<MovieModel>> =
                localDataSource.getMovies().map {
                    MapData.mapMovieEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<MovieModel>?): Boolean = data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getMovies(s, page)

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movies = MapData.mapMovieResponsesToEntities(data)
                localDataSource.insertMovies(movies)
            }
        }.asFlow()
    }

    override fun getFavoriteMovies(): Flow<List<MovieModel>> =
        localDataSource.getFavoriteMovies().map {
            MapData.mapMovieEntitiesToDomain(it)
        }

    override fun setFavoriteMovie(movieModel: MovieModel, isFavorite: Boolean) {
        val movieEntity = MapData.mapDomainToMovieEntities(movieModel)
        appExecutors.diskIO().execute {
            localDataSource.saveFavoriteMovie(movieEntity, isFavorite)
        }
    }

    override fun getMovieDetail(movieId: Long): Flow<MovieModel> =
        localDataSource.getMovieDetail(movieId).map{
            MapData.mapMovieEntityToDomain(it)
        }
}