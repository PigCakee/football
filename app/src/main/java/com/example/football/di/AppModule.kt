package com.example.football.di

import android.content.Context
import com.example.football.data.db.PlayerDatabase
import com.example.football.data.repository.PlayersRepository
import com.example.football.ui.clubs.ClubsPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun playerDatabase(context: Context) = PlayerDatabase.getDatabase(context)
}
