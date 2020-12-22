package com.example.football.data.repository

import com.example.football.data.db.PlayerDatabase
import com.example.football.data.entity.Player
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class PlayersRepository @Inject constructor(
    playerDatabase: PlayerDatabase
) {
    private val dao = playerDatabase.dao()

    fun getPlayersByClub(club: String): Observable<List<Player>> {
        return dao.getPlayersByClub(club)
    }

    fun getPlayersByPosition(position: String): Observable<List<Player>> {
        return dao.getPlayersByPosition(position)
    }

    fun getPlayersByNationality(nationality: String): Observable<List<Player>> {
        return dao.getPlayersByNationality(nationality)
    }

    fun getAllPlayersSingle(): Single<List<Player>> = dao.getPlayersSingle()

    fun getPositionsInClub(club: String): Observable<List<String>> {
        return dao.getPlayers().map { list ->
            list.filter { it.club == club }
                .map { it.position }
                .distinct()
        }
    }

    fun getNationalitiesInCLub(club: String): Observable<List<String>> {
        return dao.getPlayers().map { list ->
            list.filter { it.club == club }
                .map { it.nationality }
                .distinct()
        }
    }

    fun getClubsWithNationality(nationality: String): Observable<List<String>> {
        return dao.getPlayers().map { list ->
            list.filter { it.nationality == nationality }
                .map { it.club }
                .distinct()
        }
    }

    fun getClubsWithPosition(position: String): Observable<List<String>> {
        return dao.getPlayers().map { list ->
            list.filter { it.position == position }
                .map { it.club }
                .distinct()
        }
    }

    fun getPositionsWithNationality(nationality: String): Observable<List<String>> {
        return dao.getPlayers().map { list ->
            list.filter { it.nationality == nationality }
                .map { it.position }
                .distinct()
        }
    }

    fun getNationalitiesWithPosition(position: String): Observable<List<String>> {
        return dao.getPlayers().map { list ->
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
    ): Observable<List<Player>> {
        return dao.getPlayersByNationality(nationality).map { list ->
            list.filter { it.position == position }
        }
    }

    fun getPlayersByPositionInClub(position: String, club: String): Observable<List<Player>> {
        return dao.getPlayersByPosition(position).map { list ->
            list.filter { it.club == club }
        }
    }

    fun getPlayersByNationalityInClub(nationality: String, club: String): Observable<List<Player>> {
        return dao.getPlayersByNationality(nationality).map { list ->
            list.filter { it.club == club }
        }
    }

    fun updatePlayer(player: Player) {
        Completable.fromRunnable { dao.updatePlayer(player) }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}