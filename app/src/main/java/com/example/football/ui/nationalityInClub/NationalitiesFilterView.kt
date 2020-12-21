package com.example.football.ui.nationalityInClub

import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface NationalitiesFilterView : MvpView {
    @StateStrategyType(value = SingleStateStrategy::class)
    fun setRecyclerData(list: List<String>)
}