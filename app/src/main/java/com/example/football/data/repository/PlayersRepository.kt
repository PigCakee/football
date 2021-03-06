package com.example.football.data.repository

import com.example.football.data.db.PlayerDatabase
import com.example.football.data.entity.Player
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayersRepository @Inject constructor(
    playerDatabase: PlayerDatabase
) {
    private var dao = playerDatabase.dao()

    fun getPlayersByClub(club: String): Observable<List<Player>> {
        return dao.getPlayersByClub(club)
    }

    fun getPlayersByPosition(position: String): Observable<List<Player>> {
        return dao.getPlayersByPosition(position)
    }

    fun getPlayersByNationality(nationality: String): Observable<List<Player>> {
        return dao.getPlayersByNationality(nationality)
    }

    fun getAllPlayers(): Observable<List<Player>> = dao.getPlayers()

    fun getPositionsInClub(club: String): Single<List<String>> {
        return dao.getPlayersSingle().map { list ->
            list.filter { it.club == club }
                .map { it.position }
                .distinct()
        }
    }

    fun getNationalitiesInCLub(club: String): Single<List<String>> {
        return dao.getPlayersSingle().map { list ->
            list.filter { it.club == club }
                .map { it.nationality }
                .distinct()
        }
    }

    fun getClubsWithNationality(nationality: String): Single<List<String>> {
        return dao.getPlayersSingle().map { list ->
            list.filter { it.nationality == nationality }
                .map { it.club }
                .distinct()
        }
    }

    fun getClubsWithPosition(position: String): Single<List<String>> {
        return dao.getPlayersSingle().map { list ->
            list.filter { it.position == position }
                .map { it.club }
                .distinct()
        }
    }

    fun getPositionsWithNationality(nationality: String): Single<List<String>> {
        return dao.getPlayersSingle().map { list ->
            list.filter { it.nationality == nationality }
                .map { it.position }
                .distinct()
        }
    }

    fun getNationalitiesWithPosition(position: String): Single<List<String>> {
        return dao.getPlayersSingle().map { list ->
            list.filter { it.position == position }
                .map { it.nationality }
                .distinct()
        }
    }

    fun getAllPositions(): Observable<List<String>> {
        return dao.getPlayers().map { list -> list.map { it.position }.distinct() }
    }

    fun getAllNationalities(): Observable<List<String>> {
        return dao.getPlayers().map { list -> list.map { it.nationality }.distinct() }
    }

    fun getAllClubs(): Observable<List<String>> {
        return dao.getPlayers().map { list -> list.map { it.club }.distinct() }
    }

    fun getPlayersWithNationalityInPosition(
        nationality: String,
        position: String
    ): Single<List<Player>> {
        return dao.getPlayersByNationalitySingle(nationality).map { list ->
            list.filter { it.position == position }
        }
    }

    fun getPlayersByPositionInClub(position: String, club: String): Single<List<Player>> {
        return dao.getPlayersByPositionSingle(position).map { list ->
            list.filter { it.club == club }
        }
    }

    fun getPlayersByNationalityInClub(nationality: String, club: String): Single<List<Player>> {
        return dao.getPlayersByNationalitySingle(nationality).map { list ->
            list.filter { it.club == club }
        }
    }

    fun updatePlayer(player: Player) {
        Completable.fromRunnable { dao.updatePlayer(player) }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}