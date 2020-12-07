package com.example.football.model.repo

import com.example.football.model.club.Player
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

    fun getAllPlayers(): Flow<List<Player>> = flow { emit(playersData) }

    fun getAllPositions(): Flow<List<String>> {
        val positions: MutableList<String> = mutableListOf()

        playersData.forEach {
            if (!positions.contains(it.position)) {
                positions.add(it.position)
            }
        }
        return flow { emit(positions as List<String>) }
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

    fun getPlayersByPositionInClub(position: String, club: String): Flow<List<Player>> {
        return flow { emit(playersData.filter { it.club == club && it.position == position }) }
    }

    companion object {
        private val playersData = listOf(
            Player("Anton", "Goalkeeper", "German","Bavaria"),
            Player("Anton1", "Forward", "Belarussian","Bavaria"),
            Player("Anton2", "Forward", "English","Bavaria"),
            Player("Anton3", "Defence", "Saxon","Bavaria"),
            Player("Anton4", "Middle", "Polish","Bavaria"),
            Player("Anton5", "Forward", "Belarussian","Bavaria"),
            Player("Anton6", "Forward", "Belarussian","Bavaria")
        )

        //private val clubsData = listOf(
        //    Club(
        //        "Bavaria",
        //        listOf(
        //            Player("Anton", "Goalkeeper", "German"),
        //            Player("Anton1", "Forward", "Belarussian"),
        //            Player("Anton2", "Forward", "English"),
        //            Player("Anton3", "Defence", "Saxon"),
        //            Player("Anton4", "Middle", "Polish"),
        //            Player("Anton5", "Forward", "Belarussian"),
        //            Player("Anton6", "Forward", "Belarussian")
        //        )
        //    ),
        //    Club(
        //        "PSG", listOf(
        //            Player("Anton", "Goalkeeper", "German"),
        //            Player("Anton1", "Forward", "Belarussian"),
        //            Player("Anton2", "Forward", "English"),
        //            Player("Anton3", "Defence", "Saxon"),
        //            Player("Anton4", "Middle", "Polish"),
        //            Player("Anton5", "Middle", "Polish")
        //        )
        //    ),
        //    Club(
        //        "Juventus", listOf(
        //            Player("Anton", "Goalkeeper", "German"),
        //            Player("Anton1", "Forward", "Belarussian"),
        //            Player("Anton2", "Forward", "English"),
        //            Player("Anton3", "Defence", "Saxon"),
        //            Player("Anton4", "Middle", "Polish")
        //        )
        //    ),
        //    Club(
        //        "Tottanham", listOf(
        //            Player("Anton", "Goalkeeper", "German"),
        //            Player("Anton1", "Forward", "Belarussian"),
        //            Player("Anton2", "Forward", "English"),
        //            Player("Anton3", "Defence", "Saxon"),
        //            Player("Anton4", "Middle", "Polish")
        //        )
        //    )
        //)
    }
}