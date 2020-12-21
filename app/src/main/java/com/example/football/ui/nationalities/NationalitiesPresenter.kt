package com.example.football.ui.nationalities

import com.example.football.data.entity.Player
import com.example.football.data.repository.PlayersRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class NationalitiesPresenter @Inject constructor(
    private val playersRepository: PlayersRepository
) : MvpPresenter<NationalitiesView>() {

    private var nationalitiesDisposable: Disposable? = null
    private var playersDisposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getAllNationalities()
    }

    override fun onDestroy() {
        super.onDestroy()
        nationalitiesDisposable?.dispose()
        playersDisposable?.dispose()
    }

    private fun getAllNationalities() {
        val list: MutableList<Pair<List<Player>, String>> = mutableListOf()
        playersRepository.getAllNationalities()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.forEach { nationality -> getPlayersWithNationality(nationality, list) }
            }
    }

    private fun getPlayersWithNationality(
        nationality: String,
        list: MutableList<Pair<List<Player>, String>>
    ) {
        playersRepository.getPlayersByNationality(nationality)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                list.add(Pair(it, nationality))
                viewState.setRecyclerData(list)
            }
    }

    fun handleNationalityClick(nationality: String) {
        viewState.moveForward(nationality)
    }
}