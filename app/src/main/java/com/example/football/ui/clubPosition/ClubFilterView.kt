package com.example.football.ui.clubPosition

import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ClubFilterView: MvpView {
    @StateStrategyType(value = SingleStateStrategy::class)
    fun setRecyclerData(list: List<String>)
}