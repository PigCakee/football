package com.example.football

import android.app.Application
import com.example.football.di.AppComponent
import com.example.football.di.DaggerAppComponent
import com.example.football.model.player.db.PlayerDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

open class MainApplication : Application() {
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }
}