package com.example.testapp

import android.app.Application
import android.util.Log
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Qualifier

@HiltAndroidApp
class TestApp : Application()

fun log(tag: String, msg: Any?) = Log.d("loggggg-$tag", "$msg")

sealed class AppTheme(val type: String) {
    object DarkTheme : AppTheme("DarkTheme")
    object LightTheme : AppTheme("LightTheme")
}

//////////////////////////////////////////////////////////
/////----------- Binding Hilt dependency ----------///////
//////////////////////////////////////////////////////////

interface AnalyticsService {
    fun track(action: Any?)
}

class AnalyticsServiceImpl @Inject constructor() : AnalyticsService {
    override fun track(action: Any?) {
        log("AnalyticsService", action)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class AnalyticsModule {

    @Binds
    abstract fun bindAnalyticsService(
        analyticsServiceImpl: AnalyticsServiceImpl
    ): AnalyticsService
}

//////////////////////////////////////////////////////////
/////----------- Provide Hilt dependency ----------///////
//////////////////////////////////////////////////////////

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Logger1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Logger2

class LoggerService(val id: Int) {
    fun log(msg: Any?) {
        log("LoggerService-$id", "$msg")
    }
}

@Module
@InstallIn(ActivityComponent::class)
object LoggerModule {
    @Logger1
    @Provides
    fun provideLoggerService1(): LoggerService {
        return LoggerService(1)
    }

    @Logger2
    @Provides
    fun provideLoggerService2(): LoggerService {
        return LoggerService(2)
    }
}

