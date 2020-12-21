package com.example.football.ui.positions

import com.example.football.data.entity.Player
import com.example.football.data.repository.PlayersRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class PositionsPresenter @Inject constructor(
    private val playersRepository: PlayersRepository
): MvpPresenter<PositionsView>() {
    private var positionsDisposable: Disposable? = null
    private var playersDisposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getPositions()
    }

    override fun onDestroy() {
        super.onDestroy()
        positionsDisposable?.dispose()
        playersDisposable?.dispose()
    }

    private fun getPositions() {
        val list: MutableList<Pair<List<Player>, String>> = mutableListOf()
        playersRepository.getAllPositions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it.forEach { position -> getPlayersByPosition(position, list) } }
    }

    private fun getPlayersByPosition(
        position: String,
        list: MutableList<Pair<List<Player>, String>>
    ) {
        playersRepository.getPlayersByPosition(position)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                list.add(Pair(it, position))
                viewState.setRecyclerData(list)
            }
    }

    fun handlePositionClick(position: String) {
        viewState.moveForward(position)
    }
}