package com.example.testtaskcompose

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.testtaskcompose.retrofit.CommonProfile
import com.example.testtaskcompose.retrofit.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {

    var usersList = mutableStateListOf<CommonProfile>()

    suspend fun loadUsers() {
        coroutineScope {
            val response = RetrofitService.getInstance().getUsers()
            if(response.isSuccessful) usersList.addAll(response.body() ?: listOf())
            else usersList.addAll(listOf())
        }
    }
}