package com.example.football

import android.app.Application
import com.example.football.di.AppComponent
import com.example.football.di.DaggerAppComponent

open class MainApplication : Application() {
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }
}