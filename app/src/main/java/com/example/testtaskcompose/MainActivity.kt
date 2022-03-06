package com.example.testtaskcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.example.testtaskcompose.ui.theme.TestTaskComposeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    val mainVM by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTaskComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val scope = rememberCoroutineScope()
                    scope.launch {
                        mainVM.loadUsers()
                        mainVM.usersList.forEach {
                            Log.d("myLog", "${it.login}")
                        }
                    }
                    Greeting("Android")
                }
            }
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