package com.example.football.model.repo

import com.example.football.model.club.Club
import com.example.football.model.club.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClubsRepository @Inject constructor() {
    val clubs: Flow<List<Club>> = flow { emit(clubsData) }

    companion object {
        private val clubsData = listOf(
            Club(
                "Bavaria",
                listOf(
                    Player("Anton", "Goalkeeper", "German"),
                    Player("Anton1", "Forward", "Belarussian"),
                    Player("Anton2", "Forward", "English"),
                    Player("Anton3", "Defence", "Saxon"),
                    Player("Anton4", "Middle", "Polish")
                )
            ),
            Club(
                "PSG", listOf(
                    Player("Anton", "Goalkeeper", "German"),
                    Player("Anton1", "Forward", "Belarussian"),
                    Player("Anton2", "Forward", "English"),
                    Player("Anton3", "Defence", "Saxon"),
                    Player("Anton4", "Middle", "Polish")
                )
            ),
            Club(
                "Juventus", listOf(
                    Player("Anton", "Goalkeeper", "German"),
                    Player("Anton1", "Forward", "Belarussian"),
                    Player("Anton2", "Forward", "English"),
                    Player("Anton3", "Defence", "Saxon"),
                    Player("Anton4", "Middle", "Polish")
                )
            ),
            Club(
                "Tottanham", listOf(
                    Player("Anton", "Goalkeeper", "German"),
                    Player("Anton1", "Forward", "Belarussian"),
                    Player("Anton2", "Forward", "English"),
                    Player("Anton3", "Defence", "Saxon"),
                    Player("Anton4", "Middle", "Polish")
                )
            )
        )
    }
}