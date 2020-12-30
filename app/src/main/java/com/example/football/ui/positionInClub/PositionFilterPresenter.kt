package com.example.football.ui.positionInClub

import com.example.football.data.repository.PlayersRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class PositionFilterPresenter @Inject constructor(
    private val playersRepository: PlayersRepository
) : MvpPresenter<PositionFilterView>() {
    var position: String? = null
    var title: String = ""

    fun getClubsWithPosition(position: String) {
        playersRepository.getClubsWithPosition(position)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it -> viewState.setRecyclerData(it) }
    }

    fun getNationalitiesWithPosition(position: String) {
        playersRepository.getNationalitiesWithPosition(position)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it -> viewState.setRecyclerData(it) }
    }
}