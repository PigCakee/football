package com.example.football.ui.clubs

import com.example.football.data.entity.Player
import com.example.football.data.repository.PlayersRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject

@InjectViewState
class ClubsPresenter @Inject constructor(
    private val playersRepository: PlayersRepository
) : MvpPresenter<ClubsView>() {
    private var clubsDisposable: Disposable? = null
    private val list: CopyOnWriteArrayList<Pair<List<Player>, String>> = CopyOnWriteArrayList()

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
                Completable.fromRunnable {
                    var contains = false
                    for ((index, pair) in list.withIndex()) {
                        if (pair.second == it.first().club) {
                            if (it.size > pair.first.size)
                                list[index] = (Pair(it, it.first().club))
                            contains = true
                            break
                        }
                    }
                    if (!contains) {
                        list.add(Pair(it, it.first().club))
                    }
                }.subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        viewState.setRecyclerData(list)
                    }
            }
    }

    fun handleClubClick(club: String) {
        viewState.moveForward(club)
    }
}