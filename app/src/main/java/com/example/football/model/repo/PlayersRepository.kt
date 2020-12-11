package com.example.football.model.repo

import com.example.football.model.player.Player
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PlayersRepository @Inject constructor() {

    fun getPlayersByClub(club: String): Flow<List<Player>> {
        return flow { emit(playersData.filter { it.club == club }) }
    }

    fun getPlayersByPosition(position: String): Flow<List<Player>> {
        return flow { emit(playersData.filter { it.position == position }) }
    }

    fun getPlayersByNationality(nationality: String): Flow<List<Player>> {
        return flow { emit(playersData.filter { it.nationality == nationality }) }
    }

    fun getAllPositions(): Flow<List<String>> {
        val positions: MutableList<String> = mutableListOf()

        playersData.forEach {
            if (!positions.contains(it.position)) {
                positions.add(it.position)
            }
        }
        return flow { emit(positions as List<String>) }
    }

    fun getPositionsInClub(club: String): Flow<List<String>> {
        val positions: MutableList<String> = mutableListOf()

        playersData.forEach {
            if (!positions.contains(it.position) && it.club == club) {
                positions.add(it.position)
            }
        }
        return flow { emit(positions as List<String>) }
    }

    fun getNationalitiesInCLub(club: String): Flow<List<String>> {
        val nationalities: MutableList<String> = mutableListOf()

        playersData.forEach {
            if (!nationalities.contains(it.nationality) && it.club == club) {
                nationalities.add(it.nationality)
            }
        }
        return flow { emit(nationalities as List<String>) }
    }

    fun getClubsWithNationality(nationality: String): Flow<List<String>> {
        val clubs: MutableList<String> = mutableListOf()

        playersData.forEach {
            if (!clubs.contains(it.club) && it.nationality == nationality) {
                clubs.add(it.club)
            }
        }
        return flow { emit(clubs as List<String>) }
    }

    fun getClubsWithPosition(position: String): Flow<List<String>> {
        val clubs: MutableList<String> = mutableListOf()

        playersData.forEach {
            if (!clubs.contains(it.club) && it.position == position) {
                clubs.add(it.club)
            }
        }
        return flow { emit(clubs as List<String>) }
    }

    fun getPositionsWithNationality(nationality: String): Flow<List<String>> {
        val positions: MutableList<String> = mutableListOf()

        playersData.forEach {
            if (!positions.contains(it.position) && it.nationality == nationality) {
                positions.add(it.position)
            }
        }
        return flow { emit(positions as List<String>) }
    }

    fun getNationalitiesWithPosition(position: String): Flow<List<String>> {
        val nationalities: MutableList<String> = mutableListOf()

        playersData.forEach {
            if (!nationalities.contains(it.nationality) && it.position == position) {
                nationalities.add(it.nationality)
            }
        }
        return flow { emit(nationalities as List<String>) }
    }

    fun getAllNationalities(): Flow<List<String>> {
        val nationalities: MutableList<String> = mutableListOf()

        playersData.forEach {
            if (!nationalities.contains(it.nationality)) {
                nationalities.add(it.nationality)
            }
        }
        return flow { emit(nationalities as List<String>) }
    }

    fun getAllClubs(): Flow<List<String>> {
        val clubs: MutableList<String> = mutableListOf()

        playersData.forEach {
            if (!clubs.contains(it.club)) {
                clubs.add(it.club)
            }
        }
        return flow { emit(clubs as List<String>) }
    }

    fun getPlayersWithNationalityInPosition(
        nationality: String,
        position: String
    ): Flow<List<Player>> {
        return flow { emit(playersData.filter { it.nationality == nationality && it.position == position }) }
    }

    fun getPlayersByPositionInClub(position: String, club: String): Flow<List<Player>> {
        return flow { emit(playersData.filter { it.club == club && it.position == position }) }
    }

    fun getPlayersByNationalityInClub(nationality: String, club: String): Flow<List<Player>> {
        return flow { emit(playersData.filter { it.nationality == nationality && it.club == club }) }
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