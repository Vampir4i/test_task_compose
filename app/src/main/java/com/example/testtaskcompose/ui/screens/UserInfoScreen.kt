package com.example.testtaskcompose.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.testtaskcompose.MainViewModel

@Composable
fun UserInfoScreen(
    mainViewModel: MainViewModel,
    userName: String
) {
    Text(text = "USER INFO $userName")
}