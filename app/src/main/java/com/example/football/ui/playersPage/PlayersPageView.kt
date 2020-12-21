package com.example.football.ui.playersPage

import com.example.football.data.entity.Player
import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface PlayersPageView: MvpView {
    @StateStrategyType(value = SingleStateStrategy::class)
    fun setPlayersOnPositionInClubData(list: List<Player>)

    @StateStrategyType(value = SingleStateStrategy::class)
    fun setPlayersWithNationalityInClubData(list: List<Player>)

    @StateStrategyType(value = SingleStateStrategy::class)
    fun setPlayersWithNationalityInPositionData(list: List<Player>)
}