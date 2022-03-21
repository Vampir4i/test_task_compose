package com.example.testtaskcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.testtaskcompose.ui.screens.AllUsersScreen
import com.example.testtaskcompose.ui.screens.UserInfoScreen
import com.example.testtaskcompose.ui.theme.TestTaskComposeTheme
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

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
            ) { url ->
                val encodedUrl = URLEncoder.encode(url, "UTF-8")
//                val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
                navController.navigate("${AllScreens.WebView.name}/$encodedUrl")
            }
        }
        composable(
            route = "${AllScreens.WebView.name}/{url}",
            arguments = listOf(
                navArgument("url") {
                    type = NavType.StringType
                }
            )
        ) {
            val url = it.arguments?.getString("url") ?: ""
            ShowWebView(url = url)
        }

    }
}

@Composable
fun TopBar(navController: NavHostController) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val appBarText = when (AllScreens.fromRoute(backStackEntry.value?.destination?.route)) {
        AllScreens.AllUsers -> "Users"
        AllScreens.UserInfo -> backStackEntry.value?.arguments?.get("name").toString()
        AllScreens.WebView -> "Profile"
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

@Composable
fun ShowWebView(url: String) {
    val webViewState = rememberWebViewState(url = url)
    WebView(state = webViewState)
}

enum class AllScreens {
    AllUsers,
    UserInfo,
    WebView;

    companion object {
        fun fromRoute(route: String?): AllScreens =
            when (route?.substringBefore("/")) {
                AllUsers.name -> AllUsers
                UserInfo.name -> UserInfo
                WebView.name -> WebView
                null -> AllUsers
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}