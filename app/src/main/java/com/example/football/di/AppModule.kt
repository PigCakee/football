package com.example.football.di

import android.content.Context
import com.example.football.data.db.PlayerDatabase
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun playerDatabase(context: Context) = PlayerDatabase.getDatabase(context)
}
