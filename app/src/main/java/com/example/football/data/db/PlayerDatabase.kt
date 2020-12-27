package com.example.football.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.football.data.entity.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Player::class], exportSchema = false, version = 2)
abstract class PlayerDatabase : RoomDatabase() {

    abstract fun dao(): PlayerDao

    private class PlayerDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.dao())
                }
            }
        }

        suspend fun populateDatabase(dao: PlayerDao) {
            playersData.forEach { dao.insert(it) }
        }
    }

    companion object {
        @Volatile
        var INSTANCE: PlayerDatabase? = null

        fun getDatabase(context: Context): PlayerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlayerDatabase::class.java,
                    "players_table"
                ).addCallback(PlayerDatabaseCallback(GlobalScope)).build()
                INSTANCE = instance
                instance
            }
        }

        private val playersData = listOf(
            Player("Manuel Neuer", "Goalkeeper", "German", "Bavaria", false),
            Player("Alexander Nübel", "Goalkeeper", "German", "Bavaria", false),
            Player("Ron Hoffmann", "Goalkeeper", "German", "Bavaria", false),
            Player("Niklas Süle", "Defender", "German", "Bavaria", false),
            Player("Benjamin Pavard", "Defender", "French", "Bavaria", false),
            Player("Jérôme Boateng", "Defender", "German", "Bavaria", false),
            Player("Alphonso Davies", "Defender", "Canadian", "Bavaria", false),
            Player("Bouna Sarr", "Defender", "French", "Bavaria", false),
            Player("Lucas Hernández", "Defender", "French", "Bavaria", false),
            Player("Joshua Kimmich", "Midfield", "German", "Bavaria", false),
            Player("Javi Martinez", "Midfield", "Spanish", "Bavaria", false),
            Player("Leon Goretzka", "Midfield", "German", "Bavaria", false),
            Player("Robert Lewandowski", "Forward", "Polish", "Bavaria", false),
            Player("Thomas Müller", "Forward", "German", "Bavaria", false),
            Player("Serge Gnabry", "Forward", "German", "Bavaria", false),
            Player("Wojciech Szczesny", "Goalkeeper", "Polish", "Juventus", false),
            Player("Gianluigi Buffon", "Goalkeeper", "Italian", "Juventus", false),
            Player("Stefano Gori", "Goalkeeper", "Italian", "Juventus", false),
            Player("Matthijs de Ligt", "Midfield", "German", "Juventus", false),
            Player("Leonardo Bonucci", "Midfield", "Italian", "Juventus", false),
            Player("Giorgio Chiellini", "Midfield", "Italian", "Juventus", false),
            Player("Álvaro Morata", "Forward", "Spanish", "Juventus", false),
            Player("Paulo Dybala", "Forward", "Italian", "Juventus", false),
            Player("Federico Bernardeschi", "Forward", "Italian", "Juventus", false),
            Player("David de Gea", "Goalkeeper", "Spanish", "Manchester United", false),
            Player("Lee Grant", "Goalkeeper", "English", "Manchester United", false),
            Player("Dean Henderson", "Goalkeeper", "English", "Manchester United", false),
            Player("Victor Lindelöf", "Defender", "Swedish", "Manchester United", false),
            Player("Phil Jones", "Defender", "English", "Manchester United", false),
            Player("Luke Shaw", "Defender", "English", "Manchester United", false),
            Player("Aaron Wan-Bissaka", "Defender", "English", "Manchester United", false),
            Player("Paul Pogba", "Midfield", "French", "Manchester United", false),
            Player("Juan Mata", "Midfield", "Spanish", "Manchester United", false),
            Player("Jesse Lingard", "Midfield", "English", "Manchester United", false),
            Player("Anthony Martial", "Forward", "French", "Manchester United", false),
            Player("Mason Greenwood", "Forward", "English", "Manchester United", false)
        )
    }
}