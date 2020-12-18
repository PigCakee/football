package com.example.football.ui.clubs

import com.example.football.data.entity.Player
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = OneExecutionStateStrategy::class)
interface ClubsView: MvpView {
    fun setRecyclerData(list: MutableList<Pair<List<Player>, String>>)
    fun moveForward(club: String)
}