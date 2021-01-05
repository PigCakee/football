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
    private var playersDisposable: Disposable? = null
    private val list: MutableList<Pair<List<Player>, String>> = mutableListOf()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getPositions()
    }

    override fun onDestroy() {
        super.onDestroy()
        playersDisposable?.dispose()
    }

    private fun getPositions() {
        playersDisposable = playersRepository.getAllPositions()
            .flatMapIterable { it }
            .flatMap { playersRepository.getPlayersByPosition(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                list.add(Pair(it, it.first().position))
                viewState.setRecyclerData(list)
            }
    }

    fun handlePositionClick(position: String) {
        viewState.moveForward(position)
    }
}