package com.example.testtaskcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
        AppNavHost(navController = navController)
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val mainVM: MainViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = AllScreens.AllUsers.name,
        modifier = modifier
    ) {
        composable(AllScreens.AllUsers.name) {
            AllUsersScreen(
                mainVM = mainVM,
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
                mainViewModel = mainVM,
                userName = userName ?: ""
            )
        }

    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TestTaskComposeTheme {
        Greeting("Android")
    }
}

enum class AllScreens {
    AllUsers,
    UserInfo;
}