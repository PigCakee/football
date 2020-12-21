package com.example.football.ui.positions

import com.example.football.data.entity.Player
import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface PositionsView: MvpView {
    @StateStrategyType(value = SingleStateStrategy::class)
    fun setRecyclerData(list: MutableList<Pair<List<Player>, String>>)
    @StateStrategyType(value = SkipStrategy::class)
    fun moveForward(position: String)
}