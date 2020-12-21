package com.example.football.ui.positionInClub

import com.example.football.data.repository.PlayersRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
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

    private var nationalitiesDisposable: Disposable? = null
    private var clubsDisposable: Disposable? = null

    override fun onDestroy() {
        super.onDestroy()
        nationalitiesDisposable?.dispose()
        clubsDisposable?.dispose()
    }

    fun getClubsWithPosition(position: String) {
        clubsDisposable = playersRepository.getClubsWithPosition(position)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { viewState.setRecyclerData(it) }
    }

    fun getNationalitiesWithPosition(position: String) {
        nationalitiesDisposable = playersRepository.getNationalitiesWithPosition(position)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { viewState.setRecyclerData(it) }
    }
}