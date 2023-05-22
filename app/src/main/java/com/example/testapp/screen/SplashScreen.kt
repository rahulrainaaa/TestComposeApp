package com.example.testapp.screen

import android.R
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.testapp.Error
import com.example.testapp.Loading
import com.example.testapp.MainViewModelLocal
import com.example.testapp.NavHostControllerLocal
import com.example.testapp.Screen
import com.example.testapp.ScreenState
import kotlinx.coroutines.delay


@Composable
fun SplashScreen() {
    val mainViewModel = MainViewModelLocal.current
    val splashScreenState by mainViewModel.splashScreenState
    val navHost: NavHostController = NavHostControllerLocal.current
    val scale = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    OvershootInterpolator(40f).getInterpolation(it)
                })
        )
        delay(1000)
        mainViewModel.webCallOnSplashScreen()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.star_big_on),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value)
        )
        Box(modifier = Modifier.padding(top = 12.dp)) {
            when (splashScreenState) {
                null -> {}
                is ScreenState.Loading -> {
                    Loading(
                        when ((splashScreenState as ScreenState.Loading).isAutoRefreshing) {
                            true -> "Manual Refreshing"
                            false -> "Auto Refreshing"
                            else -> "Loading"
                        }
                    )
                }

                is ScreenState.Error -> {
                    val msg = (splashScreenState as ScreenState.Error).cause.localizedMessage
                    Error(msg) { mainViewModel.webCallOnSplashScreen(isAutoRefresh = false) }
                }

                is ScreenState.Success -> {
                    mainViewModel.navigateTo(navHost, Screen.Dashboard, setFirstScreen = true)
                }
            }
        }
    }
}
