package com.example.football.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.football.data.entity.Player
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Query("SELECT * FROM players_table")
    fun getPlayers(): Flow<List<Player>>

    @Query("SELECT * FROM players_table")
    fun getPlayersRX(): Observable<List<Player>>

    @Query("SELECT * FROM players_table WHERE club = :club")
    fun getPlayersByClub(club: String): Flow<List<Player>>

    @Query("SELECT * FROM players_table WHERE club = :club")
    fun getPlayersByClubRX(club: String): Observable<List<Player>>

    @Query("SELECT * FROM players_table WHERE position = :position")
    fun getPlayersByPosition(position: String): Flow<List<Player>>

    @Query("SELECT * FROM players_table WHERE nationality = :nationality")
    fun getPlayersByNationality(nationality: String): Flow<List<Player>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(player: Player)
}