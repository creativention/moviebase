package com.kadon.moviebase.core.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.kadon.moviebase.core.data.source.local.LocalDataSource
import com.kadon.moviebase.core.data.source.local.entity.MovieEntity
import com.kadon.moviebase.core.data.source.local.entity.RemoteKeys
import com.kadon.moviebase.core.data.source.remote.api.ApiService
import com.kadon.moviebase.core.utils.MapData
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

private const val STARTING_PAGE_INDEX: Int = 1

@ExperimentalPagingApi
class MovieRemoteMediator(
    private val apiService: ApiService,
    private val localDataSource: LocalDataSource,
) : RemoteMediator<Int, MovieEntity>() {
    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        Timber.d("InitializeAction.LAUNCH_INITIAL_REFRESH")
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val page = when (loadType) {
            REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                val r = remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
                Timber.d("MediatorResult REFRESH : $r")
                r
            }
            PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)

                Timber.d("MediatorResult prevKey : $prevKey")
                prevKey
            }
            APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)

                Timber.d("MediatorResult nextKey : $nextKey")
                nextKey
            }
        }

        Timber.d("MediatorResult cek page: $page")

        return try {

            val response = apiService.getMovies(page = page)
            Timber.d("MediatorResult -> page : ${response.page} total : ${response.totalPages}")

            val movies = MapData.mapMovieResponsesToEntities(response.results)
            for (m in movies){
                m.page = page
            }

            val endOfPaginationReached = response.page == response.totalPages

            if (loadType == REFRESH) {
                localDataSource.clearMovies()
                localDataSource.clearRemoteKeys()
            }

            val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1
            val keys = movies.map {
                RemoteKeys(
                    movieId = it.movieId,
                    title = it.movieTitle,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
            localDataSource.insertRemoteKeys(keys)

            localDataSource.insertMovies(movies)

            Timber.d("MediatorResult endOfPaginationReached : $endOfPaginationReached")
            MediatorResult.Success(endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        val lastRemote = state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { m ->
                // Get the remote keys of the last item retrieved
                localDataSource.remoteKeysRepoId(m.movieId)
            }
        Timber.d("MediatorResult getRemoteKeyForLastItem : $lastRemote")

        return lastRemote
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                // Get the remote keys of the first items retrieved
                localDataSource.remoteKeysRepoId(movie.movieId)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieEntity>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.movieId?.let { movieId ->
                localDataSource.remoteKeysRepoId(movieId)
            }
        }
    }
}