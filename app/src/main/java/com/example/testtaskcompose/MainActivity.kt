package com.example.testtaskcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.testtaskcompose.ui.screens.AllUsersScreen
import com.example.testtaskcompose.ui.screens.UserInfoScreen
import com.example.testtaskcompose.ui.theme.TestTaskComposeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTaskComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    TestTaskComposeTheme {
        val navController = rememberNavController()
        Scaffold(
            topBar = { TopBar(navController) }
        ) { innerPadding ->
            AppNavHost(navController = navController, Modifier.padding(innerPadding))
        }

    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AllScreens.AllUsers.name,
        modifier = modifier
    ) {
        composable(AllScreens.AllUsers.name) {
            AllUsersScreen(
                selectUser = { name -> navController.navigate("${AllScreens.UserInfo.name}/$name") }
            )
        }
        composable(
            route = "${AllScreens.UserInfo.name}/{name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                }
            )
        ) {
            val userName = it.arguments?.getString("name")
            UserInfoScreen(
                userName = userName ?: ""
            )
        }

    }
}

@Composable
fun TopBar(navController: NavHostController) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val appBarText = when (AllScreens.fromRoute(backStackEntry.value?.destination?.route)) {
        AllScreens.AllUsers -> "Users"
        AllScreens.UserInfo -> backStackEntry.value?.arguments?.get("name").toString()
    }

    TopAppBar(
        title = {
            Text(text = appBarText)
        },
        navigationIcon = if (navController.previousBackStackEntry != null) {
            {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        } else { null }
    )
}

enum class AllScreens {
    AllUsers,
    UserInfo;

    companion object {
        fun fromRoute(route: String?): AllScreens =
            when (route?.substringBefore("/")) {
                AllUsers.name -> AllUsers
                UserInfo.name -> UserInfo
                null -> AllUsers
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}