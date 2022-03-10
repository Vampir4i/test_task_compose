package com.example.testtaskcompose.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testtaskcompose.MainViewModel
import com.example.testtaskcompose.retrofit.CommonProfile
import kotlinx.coroutines.launch

@Composable
fun AllUsersScreen(
    mainVM: MainViewModel,
    selectUser: (String) -> Unit
) {
    LaunchedEffect(key1 = true, block = {
        mainVM.initializeLoadUsers()
    })

    UserList(userList = mainVM.usersList, selectUser = selectUser)
}

@Composable
fun UserList(
    userList: List<CommonProfile>,
    selectUser: (String) -> Unit
) {
    LazyColumn() {
        item { Text(text = "COUNT ${userList.size}") }
        items(userList) { user ->
            Text(
                text = "${user.login}",
                modifier = Modifier
                    .padding(10.dp)
                    .clickable { selectUser(user.login ?: "") }
            )
        }
    }
}