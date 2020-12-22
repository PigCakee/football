package com.example.football.ui.main

import android.content.Context
import com.example.football.data.db.PlayerDatabase
import com.example.football.data.entity.Player
import com.example.football.data.repository.PlayersRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val playersRepository: PlayersRepository,
    private val database: PlayerDatabase
) : MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getDataFromDB()
    }

    private fun getDataFromDB() {
        playersRepository.getAllPlayersSingle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<List<Player>>(){
                override fun onSuccess(t: List<Player>?) {
                    viewState.notifyDatabaseReady()
                }
                override fun onError(e: Throwable?) { }
            })
    }

    fun backUpDatabase(context: Context) {
        val backUpPath = database.backUpDatabaseAndGetFilePath(context)
        viewState.notifyDatabaseBackedUp(backUpPath)
    }
}