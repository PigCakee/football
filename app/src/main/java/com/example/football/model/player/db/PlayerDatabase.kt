package com.example.football.model.player.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.football.model.player.Player

@Database(entities = [Player::class], exportSchema = false, version = 1)
abstract class PlayerDatabase : RoomDatabase() {

    abstract fun dao(): PlayerDao

    companion object {
        @Volatile
        var INSTANCE: PlayerDatabase? = null

        fun getDatabase(context: Context): PlayerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlayerDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}