package com.kadon.moviebase.core.data

import com.kadon.moviebase.core.data.source.local.LocalDataSource
import com.kadon.moviebase.core.data.source.remote.RemoteDataSource
import com.kadon.moviebase.core.data.source.remote.api.ApiResponse
import com.kadon.moviebase.core.data.source.remote.response.MovieResponse
import com.kadon.moviebase.core.domain.model.Movie
import com.kadon.moviebase.core.domain.repository.IMovieRepository
import com.kadon.moviebase.core.utils.MapData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMovieRepository {
    override fun getMovies(
        s: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {

            override fun loadFromDB(): Flow<List<Movie>> =
                localDataSource.getMovies().map {
                    MapData.mapMovieEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<Movie>?): Boolean = data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getMovies(s, page)

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movies = MapData.mapMovieResponsesToEntities(data)
                localDataSource.insertMovies(movies)
            }
        }.asFlow()
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> =
        localDataSource.getFavoriteMovies().map {
            MapData.mapMovieEntitiesToDomain(it)
        }

    override fun setFavoriteMovie(movie: Movie, isFavorite: Boolean) : Flow<Int>{
        val movieEntity = MapData.mapDomainToMovieEntities(movie)
        return flow {
            emit(localDataSource.saveFavoriteMovie(movieEntity, isFavorite))
        }.flowOn(Dispatchers.IO)
    }

    override fun getMovieDetail(movieId: Long): Flow<Movie> =
        localDataSource.getMovieDetail(movieId).map{
            MapData.mapMovieEntityToDomain(it)
        }
}