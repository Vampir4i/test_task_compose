package com.example.testtaskcompose.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testtaskcompose.retrofit.GitProfile
import com.example.testtaskcompose.ui.components.AvatarImage
import com.example.testtaskcompose.view_model.UserInfoStatus
import com.example.testtaskcompose.view_model.UsersViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun UserInfoScreen(
    userName: String,
    openUrl: (String) -> Unit
) {
    val vm: UsersViewModel = viewModel()
    vm.getUserInfo(userName)

    val stateRefresh = rememberSwipeRefreshState(isRefreshing = false)
    SwipeRefresh(
        state = stateRefresh,
        onRefresh = {
            vm.userInfoStatus.value = UserInfoStatus.Loading
            vm.getUserInfo(userName)
        }
    ) {
        stateRefresh.isRefreshing = false
        when (val userInfoStatus = vm.userInfoStatus.value) {
            is UserInfoStatus.Loading -> {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is UserInfoStatus.Success -> {
                val userInfo = userInfoStatus.userInfo

                UserInfo(
                    userInfo ?: GitProfile(),
                    openUrl = openUrl
                )
            }
            is UserInfoStatus.Failure -> {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Loading failed",
                        style = MaterialTheme.typography.h6,
                        color = Color.Red
                    )
                    Text("Swipe to try again")
                }
            }
        }
    }


}

@Composable
fun UserInfo(
    user: GitProfile,
    openUrl: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AvatarImage(
            url = user.avatarUrl ?: "",
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
        )
        Divider(
            Modifier
                .height(20.dp)
                .width(0.dp)
        )
        Text(
            user.login ?: "",
            fontSize = 20.sp
        )
        val context = LocalContext.current
        Text(
            user.htmlUrl ?: "",
            color = Color.Gray,
            modifier = Modifier.clickable {
//                val webIntent: Intent = Uri.parse(user.htmlUrl).let { webpage ->
//                    Intent(Intent.ACTION_VIEW, webpage)
//                }
//                startActivity(context, webIntent, null)
                openUrl(user.htmlUrl ?: "")
            }
        )
        Divider(
            Modifier
                .height(20.dp)
                .width(0.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "Repos: ${user.publicRepos}")
            Text(text = "Gists: ${user.publicGists}")
            Text(text = "Followers: ${user.followers}")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PUserInfo() {
    UserInfo(
        GitProfile(
            login = "Test Testovich",
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            url = "https://api.github.com/users/mojombo"
        ), {}
    )
}