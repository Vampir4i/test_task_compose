package com.example.testtaskcompose.view_model

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.testtaskcompose.retrofit.CommonProfile
import com.example.testtaskcompose.retrofit.RetrofitService
import com.example.testtaskcompose.ui.paging.UsersSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UsersViewModel : ViewModel() {


    fun getAllUsers(): Flow<PagingData<CommonProfile>> {
        return Pager(PagingConfig(10)) { UsersSource() }.flow
//            .cachedIn(viewModelScope)
    }
}
