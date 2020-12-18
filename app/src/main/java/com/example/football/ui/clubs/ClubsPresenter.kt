package com.example.football.ui.clubs

import com.example.football.data.entity.Player
import com.example.football.data.repository.PlayersRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class ClubsPresenter @Inject constructor(
    private val playersRepository: PlayersRepository
) : MvpPresenter<ClubsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getClubs()
    }

    private fun getClubs() {
        val list: MutableList<Pair<List<Player>, String>> = mutableListOf()
        playersRepository.getAllClubsRX()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<List<String>>() {
                override fun onNext(it: List<String>?) {
                    it?.forEach { club -> getPlayersByClub(club, list) }
                }

                override fun onError(e: Throwable?) {}
                override fun onComplete() {}
            })
    }

    private fun getPlayersByClub(club: String, list: MutableList<Pair<List<Player>, String>>) {
        playersRepository.getPlayersByClubRX(club)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<List<Player>>() {
                override fun onNext(it: List<Player>?) {
                    it?.let { list.add(Pair(it, club)) }
                    viewState.setRecyclerData(list)
                }

                override fun onError(e: Throwable?) {}
                override fun onComplete() {}
            })
    }

    fun handleClubClick(club: String) {
        viewState.moveForward(club)
    }
}