package com.example.football.ui.clubPosition

import com.example.football.data.repository.PlayersRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
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

    private var positionsDisposable: Disposable? = null
    private var nationalitiesDisposable: Disposable? = null

    override fun onDestroy() {
        super.onDestroy()
        positionsDisposable?.dispose()
        nationalitiesDisposable?.dispose()
    }

    fun getPositionsInClub(club: String) {
        positionsDisposable = playersRepository.getPositionsInClub(club)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { viewState.setRecyclerData(it) }
    }

    fun getNationalitiesInClub(club: String) {
        nationalitiesDisposable = playersRepository.getNationalitiesInCLub(club)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { viewState.setRecyclerData(it) }
    }
}