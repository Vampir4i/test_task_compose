package com.example.testtaskcompose.ui.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testtaskcompose.retrofit.CommonProfile
import com.example.testtaskcompose.retrofit.RetrofitService

//NOT WORKING
class UsersSource : PagingSource<Int, CommonProfile>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommonProfile> {
        val nextPage = params.key ?: 0
        val usersResponse =
            RetrofitService.getInstance().getUsers(nextPage * 5, 5)
        return if (usersResponse.body() == null) LoadResult.Error(
            Exception(
                usersResponse.errorBody().toString()
            )
        )
        else LoadResult.Page(
            data = usersResponse.body() ?: listOf(),
            prevKey = if (nextPage == 0) null else nextPage - 1,
            nextKey = nextPage.plus(1)
        )
    }

    override fun getRefreshKey(state: PagingState<Int, CommonProfile>): Int? {
        return state.anchorPosition
    }
}