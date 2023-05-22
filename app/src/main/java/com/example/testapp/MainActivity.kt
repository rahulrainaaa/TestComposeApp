package com.example.testapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController
import com.example.testapp.screen.MainScreen
import com.example.testapp.ui.theme.TestAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Logger1
    @Inject
    lateinit var logger1Service: LoggerService

    @Logger2
    @Inject
    lateinit var logger2Service: LoggerService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val mainViewModel: MainViewModel by viewModels()
                    val navHost = rememberNavController()
                    CompositionLocalProvider(
                        MainViewModelLocal provides mainViewModel,
                        Logger1ServiceLocal provides logger1Service,
                        Logger2ServiceLocal provides logger2Service,
                        NavHostControllerLocal provides navHost
                    ) {
                        MainScreen()
                    }
                }
            }
        }
    }
}
