package com.example.frisbeegolf

import android.app.Application
import com.example.frisbeegolf.data.AppContainer
import com.example.frisbeegolf.data.DefaultAppContainer

class CourseInfoApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}