package com.example.testtaskcompose.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun UserInfoScreen(
//    mainViewModel: MainViewModel,
    userName: String
) {
    Text(text = "USER INFO $userName")
}