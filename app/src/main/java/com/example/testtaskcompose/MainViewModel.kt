package com.example.testtaskcompose

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtaskcompose.retrofit.CommonProfile
import com.example.testtaskcompose.retrofit.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    var usersList = mutableStateListOf<CommonProfile>()

    suspend fun loadUsers(since: Int, perPage: Int = 10) {
        val response = RetrofitService.getInstance().getUsers(since, perPage)
        if (response.isSuccessful) usersList.addAll(response.body() ?: listOf())
        else usersList.addAll(listOf())
    }

    suspend fun initializeLoadUsers(perPage: Int = 10) {
        if (usersList.isNotEmpty()) return
        loadUsers(0, perPage)
    }
}