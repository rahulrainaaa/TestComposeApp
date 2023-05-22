package com.example.testapp

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

val MainViewModelLocal = compositionLocalOf<MainViewModel> {
    error("Error::MainViewModelLocal")
}

val Logger1ServiceLocal = compositionLocalOf<LoggerService> {
    error("Error::Logger1ServiceLocal")
}

val Logger2ServiceLocal = compositionLocalOf<LoggerService> {
    error("Error::Logger2ServiceLocal")
}

val NavHostControllerLocal = compositionLocalOf<NavHostController> {
    error("Error::NavHostLocal")
}

/**
 * Contains list of all [Screen] within [NavHostController].
 */
sealed class Screen(private val tag: String) {
    object Splash : Screen("Splash")
    object Dashboard : Screen("Dashboard")
    object EMICalculator : Screen("EMICalculator")
    object MediaPlayer : Screen("MediaPlayer")
    object Registration : Screen("Registration")

    override fun toString() = this.tag

    companion object {
        fun toScreen(tag: String): Screen? {
            return when (tag) {
                Splash.toString() -> Splash
                Dashboard.toString() -> Dashboard
                EMICalculator.toString() -> EMICalculator
                MediaPlayer.toString() -> MediaPlayer
                Registration.toString() -> Registration
                else -> null
            }
        }
    }
}


suspend fun CoroutineScope.mockedCall(ms: Long): Long {
    delay(ms)
    return System.currentTimeMillis()
}

sealed class ScreenState {
    class Loading(val isAutoRefreshing: Boolean? = null) : ScreenState()
    class Success(val response: Any?) : ScreenState()
    class Error(val cause: Throwable) : ScreenState()
}

// Loading: swipe-refresh, auto-refresh-interval
//          new-loading, reloading-post-error
