package com.example.football.di

import android.content.Context
import com.example.football.ui.clubPosition.ClubPositionsFragment
import com.example.football.ui.clubs.ClubsFragment
import com.example.football.ui.main.MainActivity
import com.example.football.ui.nationalities.NationalitiesFragment
import com.example.football.ui.nationalityInClub.NationalitiesInClubsFragment
import com.example.football.ui.playersPage.PlayersPageFragment
import com.example.football.ui.positionInClub.PositionInClubsFragment
import com.example.football.ui.positions.PositionsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: ClubsFragment)
    fun inject(fragment: ClubPositionsFragment)
    fun inject(fragment: PlayersPageFragment)
    fun inject(fragment: NationalitiesFragment)
    fun inject(fragment: NationalitiesInClubsFragment)
    fun inject(fragment: PositionsFragment)
    fun inject(fragment: PositionInClubsFragment)
}
