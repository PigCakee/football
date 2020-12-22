package com.example.football.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.football.data.entity.Player
import com.example.football.utils.view.DATABASE_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileWriter


@Database(entities = [Player::class], exportSchema = false, version = 1)
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

    fun backUpDatabaseAndGetFilePath(context: Context): String {
        val dbFile: File = context.getDatabasePath(DATABASE_NAME)
        val dbBackUpFile = createFileAndSaveInInternalStorage(context)
        copy(dbFile, dbBackUpFile)
        return dbBackUpFile.toString()
    }

    private fun createFileAndSaveInInternalStorage(
        context: Context
    ): File? {
        val dir = File(context.filesDir, BACKUP)
        if (!dir.exists()) {
            dir.mkdir()
        }
        try {
            val file = File(dir, BACKUP)
            val writer = FileWriter(file)
            writer.flush()
            writer.close()
            return file
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun copy(src: File?, dst: File?) {
        FileInputStream(src).use { `in` ->
            FileOutputStream(dst).use { out ->
                // Transfer bytes from in to out
                val buf = ByteArray(1024)
                var len: Int
                while (`in`.read(buf).also { len = it } > 0) {
                    out.write(buf, 0, len)
                }
            }
        }
    }

    companion object {
        @Volatile
        var INSTANCE: PlayerDatabase? = null

        const val BACKUP = "back_up"

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