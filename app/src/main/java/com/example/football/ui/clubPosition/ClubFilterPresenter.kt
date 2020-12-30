package com.example.football.ui.clubPosition

import com.example.football.data.repository.PlayersRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class ClubFilterPresenter @Inject constructor(
    private val playersRepository: PlayersRepository
): MvpPresenter<ClubFilterView>() {
    var club: String? = null
    var title: String = ""

    fun getPositionsInClub(club: String) {
            playersRepository.getPositionsInClub(club)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it -> viewState.setRecyclerData(it) }
    }

    fun getNationalitiesInClub(club: String) {
            playersRepository.getNationalitiesInCLub(club)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it -> viewState.setRecyclerData(it) }
    }
}