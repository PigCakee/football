package com.example.football.ui.clubs

import com.example.football.data.entity.Player
import com.example.football.data.repository.PlayersRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class ClubsPresenter @Inject constructor(
    private val playersRepository: PlayersRepository
) : MvpPresenter<ClubsView>() {

    private var clubsDisposable: Disposable? = null
    private var playersDisposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getClubs()
    }

    override fun onDestroy() {
        super.onDestroy()
        clubsDisposable?.dispose()
        playersDisposable?.dispose()
    }

    private fun getClubs() {
        val list: MutableList<Pair<List<Player>, String>> = mutableListOf()
        clubsDisposable = playersRepository.getAllClubs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it?.forEach { club -> getPlayersByClub(club, list) } }
    }

    private fun getPlayersByClub(club: String, list: MutableList<Pair<List<Player>, String>>) {
        playersDisposable = playersRepository.getPlayersByClub(club)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it?.let { list.add(Pair(it, club)) }
                viewState.setRecyclerData(list)
            }
    }

    fun handleClubClick(club: String) {
        viewState.moveForward(club)
    }
}