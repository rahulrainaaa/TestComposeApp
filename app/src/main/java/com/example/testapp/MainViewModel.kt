package com.example.testapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    var analyticsService: AnalyticsService
) : ViewModel() {

    var count = mutableStateOf(value = 0)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                Thread.sleep(1000)
                var num by count
                num += 1
            }
        }
    }

    fun navigateTo(navController: NavController, screen: Screen, args: Map<String, Any?>? = null, setFirstScreen: Boolean = false) {
        navController.navigate(screen.toString())
        if (setFirstScreen) navController.graph.clear()
    }

    val splashScreenState = mutableStateOf<ScreenState?>(value = null)

    fun webCallOnSplashScreen(isAutoRefresh: Boolean? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            splashScreenState.value = ScreenState.Loading(isAutoRefresh)
            val response = mockedCall(2000)
            if (response % 2L == 0L) splashScreenState.value = ScreenState.Success(response)
            else splashScreenState.value = ScreenState.Error(Throwable("Odd number $response"))
        }
    }
}