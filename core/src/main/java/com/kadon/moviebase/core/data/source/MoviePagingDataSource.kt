package com.kadon.moviebase.core.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kadon.moviebase.core.data.source.remote.api.ApiService
import com.kadon.moviebase.core.data.source.remote.response.MovieResponse
import com.kadon.moviebase.core.utils.K

class
oviePagingDataSource(private val apiService: ApiService): PagingSource<Int, MovieResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        return try {
            val nextPage = params.key ?: 1
            val movieResponse = apiService.getMoviesWithPaging(category = K.CATEGORY_POPULAR, page = nextPage)
            LoadResult.Page(
                data = movieResponse.results,
                prevKey = if (nextPage == 1) null else nextPage.minus(1) ,
                nextKey = movieResponse.page.plus(1)
            )

        } catch (exception: Exception){
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        TODO("Not yet implemented")
    }
}