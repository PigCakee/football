package com.example.football.di

import android.content.Context
import android.content.SharedPreferences
import com.example.football.data.db.PlayerDatabase
import com.example.football.data.repository.PlayersRepository
import com.example.football.ui.clubs.ClubsPresenter
import com.example.football.utils.view.APP_PREFERENCES
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun playerDatabase(context: Context) = PlayerDatabase.getDatabase(context)

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(
            APP_PREFERENCES, Context.MODE_PRIVATE
        )
}
