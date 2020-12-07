package com.example.football.di

import com.example.football.ui.main.MainActivity
import com.example.football.ui.nationalities.NationalitiesFragment
import com.example.football.ui.nationalities.NationalitiesInClubsFragment
import com.example.football.ui.nationalities.NationalitiesInClubsPageFragment
import dagger.Subcomponent

@Subcomponent
interface NationalitiesComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): NationalitiesComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: NationalitiesFragment)
    fun inject(fragment: NationalitiesInClubsFragment)
    fun inject(fragment: NationalitiesInClubsPageFragment)
}
