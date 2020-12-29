package com.example.football.ui.main

import com.example.football.data.entity.Player
import com.example.football.data.repository.PlayersRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val playersRepository: PlayersRepository
) : MvpPresenter<MainView>() {
    private var playersDisposable: Disposable? = null
    var players: MutableList<Player> = mutableListOf()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getDataFromDB()
    }

    override fun onDestroy() {
        super.onDestroy()
        playersDisposable?.dispose()
    }

    private fun getDataFromDB() {
        playersDisposable = playersRepository.getAllPlayersSingle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewState.notifyDatabaseReady()
                players = it as MutableList<Player>
            }
    }

    fun checkpoint() {
        playersRepository.checkpoint()
    }
}