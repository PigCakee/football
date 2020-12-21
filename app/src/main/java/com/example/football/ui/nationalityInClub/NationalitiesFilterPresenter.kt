package com.example.football.ui.nationalityInClub

import com.example.football.data.repository.PlayersRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class NationalitiesFilterPresenter @Inject constructor(
    private val playersRepository: PlayersRepository
): MvpPresenter<NationalitiesFilterView>() {
    var nationality: String? = null
    var title: String = ""

    private var positionsDisposable: Disposable? = null
    private var clubsDisposable: Disposable? = null

    override fun onDestroy() {
        super.onDestroy()
        positionsDisposable?.dispose()
        clubsDisposable?.dispose()
    }

    fun getClubsWithNationality(nationality: String) {
        clubsDisposable = playersRepository.getClubsWithNationality(nationality)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { viewState.setRecyclerData(it) }
    }

    fun getPositionsWithNationality(nationality: String) {
        positionsDisposable = playersRepository.getPositionsWithNationality(nationality)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { viewState.setRecyclerData(it) }
    }
}