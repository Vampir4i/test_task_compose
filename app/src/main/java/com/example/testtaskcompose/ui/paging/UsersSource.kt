package com.example.testtaskcompose.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testtaskcompose.retrofit.CommonProfile
import com.example.testtaskcompose.retrofit.RetrofitService

class UsersSource : PagingSource<Int, CommonProfile>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommonProfile> {
        val nextPage = params.key ?: 0
        val usersResponse =
            RetrofitService.getInstance().getUsers(nextPage, 10)
        return if (usersResponse.body() == null) LoadResult.Error(
            Exception(
                usersResponse.errorBody().toString()
            )
        )
        else LoadResult.Page(
            data = usersResponse.body() ?: listOf(),
            prevKey = null,
//            prevKey = if (nextPage == 0) null else nextPage - 1,
            nextKey = usersResponse.body()?.last()?.id ?: 0
        )
    }

    override fun getRefreshKey(state: PagingState<Int, CommonProfile>): Int? {
        return state.anchorPosition
    }
}