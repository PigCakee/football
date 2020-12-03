package com.example.football.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppSubcomponents::class, AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun clubsComponent(): ClubsComponent.Factory
    fun nationalitiesComponent(): NationalitiesComponent.Factory
    fun positionsComponent(): PositionsComponent.Factory
}
