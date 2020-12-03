package com.example.football.di

import com.example.football.ui.clubs.ClubsFragment
import com.example.football.ui.main.MainActivity
import dagger.Subcomponent

@Subcomponent
interface ClubsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ClubsComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: ClubsFragment)
}
