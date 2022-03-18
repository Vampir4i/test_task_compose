package com.example.testtaskcompose.view_model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.testtaskcompose.retrofit.CommonProfile
import com.example.testtaskcompose.retrofit.GitProfile
import com.example.testtaskcompose.retrofit.RetrofitService
import com.example.testtaskcompose.ui.paging.UsersSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UsersViewModel : ViewModel() {

    var userInfoStatus = mutableStateOf<UserInfoStatus>(UserInfoStatus.Loading)

    fun getAllUsers(): Flow<PagingData<CommonProfile>> {
        return Pager(PagingConfig(10)) { UsersSource() }.flow
    }

    fun getUserInfo(userName: String) {
        if(userInfoStatus.value is UserInfoStatus.Success) return
        viewModelScope.launch {
            val response = RetrofitService.getInstance().getUser(userName)
            delay(2000)
            userInfoStatus.value = if (response.isSuccessful) UserInfoStatus.Success(response.body())
            else UserInfoStatus.Failure(response.message())
        }
    }
}

sealed class UserInfoStatus {
    object Loading : UserInfoStatus()
    class Success(val userInfo: GitProfile?) : UserInfoStatus()
    class Failure(val msg: String) : UserInfoStatus()
}