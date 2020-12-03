package com.example.football.di

import com.example.football.ui.main.MainActivity
import com.example.football.ui.positions.PositionFragment
import dagger.Subcomponent

@Subcomponent
interface PositionsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): PositionsComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: PositionFragment)
}
