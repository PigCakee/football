package com.example.football.ui.playersPage

import com.example.football.data.entity.Player
import com.example.football.data.repository.PlayersRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class PlayersPagePresenter @Inject constructor(
    private val playersRepository: PlayersRepository
) : MvpPresenter<PlayersPageView>() {

    fun getPlayersByPositionInClub(position: String, club: String) {
            playersRepository.getPlayersByPositionInClub(position, club)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it -> viewState.setPlayersOnPositionInClubData(it) }
    }

    fun getPlayersByNationalityInClub(nationality: String, club: String) {
            playersRepository.getPlayersByNationalityInClub(nationality, club)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it -> viewState.setPlayersWithNationalityInClubData(it) }
    }

    fun getPlayersWithNationalityInPosition(nationality: String, position: String) {
            playersRepository.getPlayersWithNationalityInPosition(nationality, position)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it -> viewState.setPlayersWithNationalityInPositionData(it) }
    }

    fun updatePlayer(player: Player) {
        playersRepository.updatePlayer(player)
    }
}