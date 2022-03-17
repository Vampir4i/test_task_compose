package com.example.testtaskcompose.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testtaskcompose.view_model.UserInfoStatus
import com.example.testtaskcompose.view_model.UsersViewModel

@Composable
fun UserInfoScreen(userName: String) {
    val vm: UsersViewModel = viewModel()
    vm.getUserInfo(userName)

    when(val userInfoStatus = vm.userInfoStatus.value) {
        is UserInfoStatus.Loading -> {
            Text("Loading")
        }
        is UserInfoStatus.Success -> {
            val userInfo = userInfoStatus.userInfo
            Row() {
                Text("${ userInfo?.login }")
                Text("${ userInfo?.url }")
            }
        }
        is UserInfoStatus.Failure -> {
            Text("Error")
        }
    }
}