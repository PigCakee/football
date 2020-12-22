package com.example.football.data.db

import androidx.room.*
import com.example.football.data.entity.Player
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Query("SELECT * FROM players_table")
    fun getPlayers(): Observable<List<Player>>

    @Query("SELECT * FROM players_table")
    fun getPlayersSingle(): Single<List<Player>>

    @Query("SELECT * FROM players_table WHERE club = :club")
    fun getPlayersByClub(club: String): Observable<List<Player>>

    @Query("SELECT * FROM players_table WHERE position = :position")
    fun getPlayersByPosition(position: String): Observable<List<Player>>

    @Query("SELECT * FROM players_table WHERE nationality = :nationality")
    fun getPlayersByNationality(nationality: String): Observable<List<Player>>

    @Update
    fun updatePlayer(player: Player)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(player: Player)
}