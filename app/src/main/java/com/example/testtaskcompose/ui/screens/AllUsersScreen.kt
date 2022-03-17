package com.example.testtaskcompose.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.testtaskcompose.retrofit.CommonProfile
import com.example.testtaskcompose.ui.components.UsersListItem
import com.example.testtaskcompose.view_model.UsersViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.Flow

@Composable
fun AllUsersScreen(selectUser: (String) -> Unit) {
    val vm: UsersViewModel = viewModel()

    PagingUserList(
        users = vm.getAllUsers(),
        selectUser = selectUser
    )
}


@Composable
fun PagingUserList(
    users: Flow<PagingData<CommonProfile>>,
    selectUser: (String) -> Unit
) {
    val stateRefresh = rememberSwipeRefreshState(isRefreshing = false)
    val lazyItems = users.collectAsLazyPagingItems()
    SwipeRefresh(
        state = stateRefresh,
        onRefresh = {
            stateRefresh.isRefreshing = false
            lazyItems.refresh()
        }) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(lazyItems.itemCount) { index ->
                    lazyItems[index]?.let { user ->
                        UsersListItem(
                            profile = user,
                            modifier = Modifier.clickable { selectUser(user.login ?: "") }
                        )
                    }
                }
            }
        }
}