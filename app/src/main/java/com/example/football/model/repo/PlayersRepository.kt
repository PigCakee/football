package com.example.football.model.repo

import com.example.football.model.player.Player
import com.example.football.model.player.db.PlayerDatabase
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