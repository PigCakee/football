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
    private val list: MutableList<Pair<List<Player>, String>> = mutableListOf()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getClubs()
    }

    override fun onDestroy() {
        super.onDestroy()
        clubsDisposable?.dispose()
    }

    private fun getClubs() {
        clubsDisposable = playersRepository.getAllClubs()
            .flatMapIterable { it }
            .flatMap { playersRepository.getPlayersByClub(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                list.add(Pair(it, it.first().club))
                viewState.setRecyclerData(list)
            }
    }

    fun handleClubClick(club: String) {
        viewState.moveForward(club)
    }
}