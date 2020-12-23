package com.example.football.ui.main

import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MainView: MvpView {
    @StateStrategyType(value = SingleStateStrategy::class)
    fun notifyDatabaseReady()
    @StateStrategyType(value = SingleStateStrategy::class)
    fun notifyDatabaseBackedUp(path: String)
    @StateStrategyType(value = SingleStateStrategy::class)
    fun notifyDatabaseRestored()
    @StateStrategyType(value = SingleStateStrategy::class)
    fun notifyBackUpDoesNotExist()
}