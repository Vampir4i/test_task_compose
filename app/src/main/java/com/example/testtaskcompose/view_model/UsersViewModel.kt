package com.example.testtaskcompose.view_model

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.testtaskcompose.retrofit.CommonProfile
import com.example.testtaskcompose.retrofit.RetrofitService
import com.example.testtaskcompose.ui.paging.UsersSource
import kotlinx.coroutines.flow.Flow

class UsersViewModel : ViewModel() {

    var usersList = mutableListOf<CommonProfile>()
//    var usersList = mutableStateListOf<CommonProfile>()
    var since = 0
    var status = mutableStateOf<LoadStatus>(LoadStatus.Initialize())

    suspend fun loadUsers(since: Int, perPage: Int = 10) {
        val response = RetrofitService.getInstance().getUsers(since, perPage)
        if (response.isSuccessful) {
            usersList.addAll(response.body() ?: listOf())
            status.value = LoadStatus.Success()
            this.since = usersList.last().id ?: 0
        } else {
            status.value = LoadStatus.Failure(response.message())
        }
    }

    suspend fun initializeLoadUsers(perPage: Int = 10) {
        if (usersList.isNotEmpty()) return
        status.value = LoadStatus.Initialize()
        loadUsers(0, perPage)
    }

    fun getAllUsers(): Flow<PagingData<CommonProfile>> {
        return Pager(PagingConfig(5)) { UsersSource() } .flow
    }
}

sealed class LoadStatus {
    class Success(): LoadStatus()
    class Initialize(): LoadStatus()
    class LoadMore(): LoadStatus()
    class Failure(var msg: String): LoadStatus()
}