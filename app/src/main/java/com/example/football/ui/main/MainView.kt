package com.example.football.ui.main

import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MainView: MvpView {
    @StateStrategyType(value = SingleStateStrategy::class)
    fun notifyDatabaseReady()
}