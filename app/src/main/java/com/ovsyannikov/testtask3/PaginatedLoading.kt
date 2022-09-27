package com.ovsyannikov.testtask3

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ovsyannikov.testtask3.api.PersonsApi
import com.ovsyannikov.testtask3.data.persons.Result

class PaginatedLoading(private val loader: PersonsApi) : PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {

            val nextPage: Int = params.key ?: FIRST_PAGE
            val response = loader.fetchPersons(nextPage)

            var nextPageNumber: Int? = null
            if (response.info.next != null) {
                val uri = Uri.parse(response.info.next)
                nextPageNumber = uri?.getQueryParameter("page")?.toInt()
            }

            var prevPageNumber: Int? = null
            if (response.info.prev != null) {
                val uri = Uri.parse(response.info.prev)
                prevPageNumber = uri?.getQueryParameter("page")?.toInt()
            }

            LoadResult.Page(data = response.results,
                prevKey = prevPageNumber,
                nextKey = nextPageNumber)

        } catch (e: Exception) {

            LoadResult.Error(throwable = e)
        }
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}