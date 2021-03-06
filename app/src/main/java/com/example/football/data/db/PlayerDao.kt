package com.example.football.data.db

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.football.data.entity.Player
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single


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

    @Query("SELECT * FROM players_table WHERE position = :position")
    fun getPlayersByPositionSingle(position: String): Single<List<Player>>

    @Query("SELECT * FROM players_table WHERE nationality = :nationality")
    fun getPlayersByNationalitySingle(nationality: String): Single<List<Player>>

    @Update
    fun updatePlayer(player: Player)

    @RawQuery
    fun checkpoint(supportSQLiteQuery: SupportSQLiteQuery?): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(player: Player)
}