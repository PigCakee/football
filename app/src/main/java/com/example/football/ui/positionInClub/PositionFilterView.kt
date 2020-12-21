package com.example.football.ui.positionInClub

import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface PositionFilterView: MvpView {
    @StateStrategyType(value = SingleStateStrategy::class)
    fun setRecyclerData(list: List<String>)
}