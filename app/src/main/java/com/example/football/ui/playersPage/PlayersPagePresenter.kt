package com.example.football.ui.playersPage

import com.example.football.data.entity.Player
import com.example.football.data.repository.PlayersRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject
import javax.inject.Singleton

@InjectViewState
class PlayersPagePresenter @Inject constructor(
    private val playersRepository: PlayersRepository
) : MvpPresenter<PlayersPageView>() {

    private var playersByPositionInClubDisposable: Disposable? = null
    private var playersByNationalityInClubDisposable: Disposable? = null
    private var playersWithNationalityInPositionDisposable: Disposable? = null

    override fun onDestroy() {
        super.onDestroy()
        playersByPositionInClubDisposable?.dispose()
        playersByNationalityInClubDisposable?.dispose()
        playersWithNationalityInPositionDisposable?.dispose()
    }

    fun getPlayersByPositionInClub(position: String, club: String) {
        playersByPositionInClubDisposable =
            playersRepository.getPlayersByPositionInClub(position, club)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { viewState.setPlayersOnPositionInClubData(it) }
    }

    fun getPlayersByNationalityInClub(nationality: String, club: String) {
        playersByNationalityInClubDisposable =
            playersRepository.getPlayersByNationalityInClub(nationality, club)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { viewState.setPlayersWithNationalityInClubData(it) }
    }

    fun getPlayersWithNationalityInPosition(nationality: String, position: String) {
        playersWithNationalityInPositionDisposable =
            playersRepository.getPlayersWithNationalityInPosition(nationality, position)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { viewState.setPlayersWithNationalityInPositionData(it) }
    }

    fun updatePlayer(player: Player) {
        playersRepository.updatePlayer(player)
    }
}