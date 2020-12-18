package com.example.football.di

import android.content.Context
import com.example.football.data.db.PlayerDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun playerDatabase(context: Context) = PlayerDatabase.getDatabase(context)
}
