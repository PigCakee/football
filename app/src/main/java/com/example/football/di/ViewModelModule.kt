package com.example.football.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.football.ui.clubPosition.ClubFilterViewModel
import com.example.football.ui.clubs.ClubsViewModel
import com.example.football.ui.nationalities.NationalitiesViewModel
import com.example.football.ui.nationalityInClub.NationalitiesFilterViewModel
import com.example.football.ui.playersPage.PlayersPageViewModel
import com.example.football.ui.positionInClub.PositionFilterViewModel
import com.example.football.ui.positions.PositionsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PositionsViewModel::class)
    internal abstract fun positionsViewModel(viewModel: PositionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ClubsViewModel::class)
    internal abstract fun clubsViewModel(viewModel: ClubsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NationalitiesViewModel::class)
    internal abstract fun nationalitiesViewModel(viewModel: NationalitiesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ClubFilterViewModel::class)
    internal abstract fun clubPositionsViewModel(viewModel: ClubFilterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NationalitiesFilterViewModel::class)
    internal abstract fun nationalitiesInClubsViewModel(viewModel: NationalitiesFilterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PositionFilterViewModel::class)
    internal abstract fun positionInClubsViewModel(viewModel: PositionFilterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlayersPageViewModel::class)
    internal abstract fun playersPageViewModel(viewModel: PlayersPageViewModel): ViewModel
}