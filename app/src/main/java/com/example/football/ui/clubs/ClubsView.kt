package com.example.football.ui.clubs

import com.example.football.data.entity.Player
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ClubsView: MvpView {
    @StateStrategyType(value = SingleStateStrategy::class)
    fun setRecyclerData(list: MutableList<Pair<List<Player>, String>>)
    @StateStrategyType(value = SkipStrategy::class)
    fun moveForward(club: String)
}