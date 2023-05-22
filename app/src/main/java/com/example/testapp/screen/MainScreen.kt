package com.example.testapp.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.testapp.NavHostControllerLocal
import com.example.testapp.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    Scaffold(
        bottomBar = { BottomBar() },
        floatingActionButton = { Text("Fab") },
        topBar = { Text("TopBar") },
        snackbarHost = { Text("SnackBarHost") },
    ) {
        NavHost(
            modifier = Modifier.padding(top = 32.dp),
            navController = NavHostControllerLocal.current,
            startDestination = Screen.Splash.toString()
        ) {

            composable(route = Screen.Splash.toString()) {
                SplashScreen()
            }

            composable(route = Screen.Dashboard.toString()) {
                DashboardScreen()
            }

            composable(route = Screen.EMICalculator.toString()) {
                EMICalculatorScreen()
            }

            composable(route = Screen.MediaPlayer.toString()) {
                Text(text = "Media Player Screen")
            }

            composable(route = Screen.Registration.toString()) {
                Text(text = "Registration Screen")
            }
        }
    }
}

@Composable
fun BottomBar() {
    BottomNavigation {

    }
}