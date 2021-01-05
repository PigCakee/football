package com.example.football

import android.app.Application
import com.example.football.di.AppComponent
import com.example.football.di.DaggerAppComponent
import com.google.firebase.FirebaseApp

class MainApplication : Application() {
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}