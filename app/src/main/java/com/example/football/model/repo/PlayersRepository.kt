package com.example.football.model.repo

import com.example.football.model.player.Player
import com.example.football.model.player.db.PlayerDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
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

    companion object {
        private val playersData = listOf(
            Player("Manuel Neuer", "Goalkeeper", "German", "Bavaria"),
            Player("Alexander Nübel", "Goalkeeper", "German", "Bavaria"),
            Player("Ron Hoffmann", "Goalkeeper", "German", "Bavaria"),
            Player("Niklas Süle", "Defender", "German", "Bavaria"),
            Player("Benjamin Pavard", "Defender", "French", "Bavaria"),
            Player("Jérôme Boateng", "Defender", "German", "Bavaria"),
            Player("Alphonso Davies", "Defender", "Canadian", "Bavaria"),
            Player("Bouna Sarr", "Defender", "French", "Bavaria"),
            Player("Lucas Hernández", "Defender", "French", "Bavaria"),
            Player("Joshua Kimmich", "Midfield", "German", "Bavaria"),
            Player("Javi Martinez", "Midfield", "Spanish", "Bavaria"),
            Player("Leon Goretzka", "Midfield", "German", "Bavaria"),
            Player("Robert Lewandowski", "Forward", "Polish", "Bavaria"),
            Player("Thomas Müller", "Forward", "German", "Bavaria"),
            Player("Serge Gnabry", "Forward", "German", "Bavaria"),
            Player("Wojciech Szczesny", "Goalkeeper", "Polish", "Juventus"),
            Player("Gianluigi Buffon", "Goalkeeper", "Italian", "Juventus"),
            Player("Stefano Gori", "Goalkeeper", "Italian", "Juventus"),
            Player("Matthijs de Ligt", "Midfield", "German", "Juventus"),
            Player("Leonardo Bonucci", "Midfield", "Italian", "Juventus"),
            Player("Giorgio Chiellini", "Midfield", "Italian", "Juventus"),
            Player("Álvaro Morata", "Forward", "Spanish", "Juventus"),
            Player("Paulo Dybala", "Forward", "Italian", "Juventus"),
            Player("Federico Bernardeschi", "Forward", "Italian", "Juventus"),
            Player("David de Gea", "Goalkeeper", "Spanish", "Manchester United"),
            Player("Lee Grant", "Goalkeeper", "English", "Manchester United"),
            Player("Dean Henderson", "Goalkeeper", "English", "Manchester United"),
            Player("Victor Lindelöf", "Defender", "Swedish", "Manchester United"),
            Player("Phil Jones", "Defender", "English", "Manchester United"),
            Player("Luke Shaw", "Defender", "English", "Manchester United"),
            Player("Aaron Wan-Bissaka", "Defender", "English", "Manchester United"),
            Player("Paul Pogba", "Midfield", "French", "Manchester United"),
            Player("Juan Mata", "Midfield", "Spanish", "Manchester United"),
            Player("Jesse Lingard", "Midfield", "English", "Manchester United"),
            Player("Anthony Martial", "Forward", "French", "Manchester United"),
            Player("Mason Greenwood", "Forward", "English", "Manchester United")
        )
    }
}