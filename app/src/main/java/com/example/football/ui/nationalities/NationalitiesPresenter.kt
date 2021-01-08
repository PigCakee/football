package com.example.football.ui.nationalities

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
class NationalitiesPresenter @Inject constructor(
    private val playersRepository: PlayersRepository
) : MvpPresenter<NationalitiesView>() {
    private var nationalitiesDisposable: Disposable? = null
    private val list: CopyOnWriteArrayList<Pair<List<Player>, String>> = CopyOnWriteArrayList()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getAllNationalities()
    }

    override fun onDestroy() {
        super.onDestroy()
        nationalitiesDisposable?.dispose()
    }

    private fun getAllNationalities() {
        nationalitiesDisposable = playersRepository.getAllNationalities()
            .flatMapIterable { it }
            .flatMap { playersRepository.getPlayersByNationality(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Completable.fromRunnable {
                    var contains = false
                    for ((index, pair) in list.withIndex()) {
                        if (pair.second == it.first().nationality) {
                            if (it.size > pair.first.size)
                                list[index] = (Pair(it, it.first().nationality))
                            contains = true
                            break
                        }
                    }
                    if (!contains) {
                        list.add(Pair(it, it.first().nationality))
                    }
                }.subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        viewState.setRecyclerData(list)
                    }
            }
    }

    fun handleNationalityClick(nationality: String) {
        viewState.moveForward(nationality)
    }
}