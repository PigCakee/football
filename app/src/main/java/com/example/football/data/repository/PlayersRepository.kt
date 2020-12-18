package com.example.football.data.repository

import com.example.football.data.entity.Player
import com.example.football.data.db.PlayerDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlayersRepository @Inject constructor(
    playerDatabase: PlayerDatabase
) {
    private val dao = playerDatabase.dao()

    fun getPlayersByClub(club: String): Flow<List<Player>> {
        return dao.getPlayersByClub(club)
    }

    fun getPlayersByClubRX(club: String): Observable<List<Player>> {
        return dao.getPlayersByClubRX(club)
    }

    fun getPlayersByPosition(position: String): Flow<List<Player>> {
        return dao.getPlayersByPosition(position)
    }

    fun getPlayersByNationality(nationality: String): Flow<List<Player>> {
        return dao.getPlayersByNationality(nationality)
    }

    fun getPositionsInClub(club: String): Flow<List<String>> {
        return dao.getPlayers().map { list ->
            list.filter { it.club == club }
                .map { it.position }
                .distinct()
        }
    }

    fun getNationalitiesInCLub(club: String): Flow<List<String>> {
        return dao.getPlayers().map { list ->
            list.filter { it.club == club }
                .map { it.nationality }
                .distinct()
        }
    }

    fun getClubsWithNationality(nationality: String): Flow<List<String>> {
        return dao.getPlayers().map { list ->
            list.filter { it.nationality == nationality }
                .map { it.club }
                .distinct()
        }
    }

    fun getClubsWithPosition(position: String): Flow<List<String>> {
        return dao.getPlayers().map { list ->
            list.filter { it.position == position }
                .map { it.club }
                .distinct()
        }
    }

    fun getPositionsWithNationality(nationality: String): Flow<List<String>> {
        return dao.getPlayers().map { list ->
            list.filter { it.nationality == nationality }
                .map { it.position }
                .distinct()
        }
    }

    fun getNationalitiesWithPosition(position: String): Flow<List<String>> {
        return dao.getPlayers().map { list ->
            list.filter { it.position == position }
                .map { it.nationality }
                .distinct()
        }
    }

    fun getAllPositions(): Flow<List<String>> {
        return dao.getPlayers().map { list -> list.map { it.position }.distinct() }
    }

    fun getAllNationalities(): Flow<List<String>> {
        return dao.getPlayers().map { list -> list.map { it.nationality }.distinct() }
    }

    fun getAllClubs(): Flow<List<String>> {
        return dao.getPlayers().map { list -> list.map { it.club }.distinct() }
    }

    fun getAllClubsRX(): Observable<List<String>> {
        return dao.getPlayersRX().map { list -> list.map { it.club }.distinct() }
    }

    fun getPlayersWithNationalityInPosition(
        nationality: String,
        position: String
    ): Flow<List<Player>> {
        return dao.getPlayersByNationality(nationality).map { list ->
            list.filter { it.position == position }
        }
    }

    fun getPlayersByPositionInClub(position: String, club: String): Flow<List<Player>> {
        return dao.getPlayersByPosition(position).map { list ->
            list.filter { it.club == club }
        }
    }

    fun getPlayersByNationalityInClub(nationality: String, club: String): Flow<List<Player>> {
        return dao.getPlayersByNationality(nationality).map { list ->
            list.filter { it.club == club }
        }
    }
}