package com.example.testtaskcompose.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.testtaskcompose.view_model.UsersViewModel
import com.example.testtaskcompose.retrofit.CommonProfile
import com.example.testtaskcompose.view_model.LoadStatus
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun AllUsersScreen(
    selectUser: (String) -> Unit
) {
    val mainVM: UsersViewModel = viewModel()
    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true, block = {
        mainVM.initializeLoadUsers()
    })

    when(val status = mainVM.status.value) {
        is LoadStatus.Initialize -> {
            CircularProgressIndicator(
                modifier =
                Modifier
                    .testTag("ProgressBarItem")
                    .padding(16.dp)
            )
        }
        is LoadStatus.Failure -> {
            Text(text = "Error ${ status.msg }")
        }
        is LoadStatus.LoadMore -> {
            UserList(
                userList = mainVM.usersList,
                selectUser = selectUser,
                loadUser = {
                    scope.launch {
                        mainVM.status.value = LoadStatus.LoadMore()
                        mainVM.loadUsers(mainVM.since)
                    }
                }
            )
            CircularProgressIndicator(
                modifier =
                Modifier
                    .testTag("ProgressBarItem")
//                    .fillMaxWidth()
                    .padding(16.dp)
//                    .wrapContentWidth(
//                        Alignment.CenterHorizontally
//                    )
            )
        }
        is LoadStatus.Success -> {
            isRefreshing = false
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                onRefresh = {
                    isRefreshing = true
                    mainVM.usersList.clear()
                    scope.launch {
                        mainVM.initializeLoadUsers()
                    }
                }) {
                UserList(
                    userList = mainVM.usersList,
                    selectUser = selectUser,
                    loadUser = {
                        scope.launch {
                            mainVM.status.value = LoadStatus.LoadMore()
                            delay(3000)
                            mainVM.loadUsers(mainVM.since)
                        }
                    }
                )
            }

        }
    }


}

@Composable
fun UserList(
    userList: List<CommonProfile>,
    selectUser: (String) -> Unit,
    loadUser: () -> Unit
) {
    LazyColumn() {
        item { Text(text = "COUNT ${userList.size}") }
        items(userList.size) { index ->
            Text(
                text = "${userList[index].id} ${userList[index].login}",
                modifier = Modifier
                    .padding(10.dp)
                    .clickable { selectUser(userList[index].login ?: "") }
            )
            if(index == userList.size - 1) loadUser()
        }
    }
}

@Composable
fun PagingUserList(
    users: Flow<PagingData<CommonProfile>>,
    selectUser: (String) -> Unit
) {
    val lazyItems = users.collectAsLazyPagingItems()
    LazyColumn() {
        item { Text(text = "COUNT ${lazyItems.itemCount}") }
        items(lazyItems.itemCount) { index ->
            lazyItems[index]?.let { user ->
                Text(
                    text = "${user.id} ${user.login}",
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable { selectUser(user.login ?: "") }
                )
            }
        }
    }
}