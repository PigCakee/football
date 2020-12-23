package com.example.football.data.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.football.data.entity.Player
import com.example.football.utils.view.DATABASE_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*

@Database(entities = [Player::class], exportSchema = false, version = 2)
abstract class PlayerDatabase : RoomDatabase() {

    abstract fun dao(): PlayerDao

    private var backUpPath: String? = null

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
        if (backUpPath == null) backUpPath = findSdCardPath()
        if (backUpPath != null) {
            val dbBackUpFile = createFileAndSaveInExternalStorage(backUpPath!!)
            Log.d("Created file", dbBackUpFile.toString())
            if (hasExternalStoragePrivateFile(backUpPath!!)) {
                copy(dbFile, dbBackUpFile)
            }
            return dbBackUpFile.toString()
        }
        return NO_SDCARD
    }

    fun restoreDatabase(context: Context): Boolean {
        val dbFile: File = context.getDatabasePath(DATABASE_NAME)
        if (backUpPath == null) backUpPath = findSdCardPath()
        if (backUpPath != null) {
            val dbBackUpFile = createFileAndSaveInExternalStorage(backUpPath!!)
            Log.d("Created file", dbBackUpFile.toString())
            if (hasExternalStoragePrivateFile(backUpPath!!)) {
                copy(dbFile, dbBackUpFile)
            }
            return true
        }
        return false
    }

    private fun hasExternalStoragePrivateFile(path: String): Boolean {
        val file = File(path, "${FOLDER}/${BACKUP}")
        Log.d("Has file", file.toString())
        Log.d("Has file", file.exists().toString())
        return file.exists()
    }

    // https://gist.github.com/PauloLuan/4bcecc086095bce28e22
    private fun findSdCardPath(): String? {
        var testDirectory: File
        sdCardsPaths.forEach {
            testDirectory = File(it)
            if (testDirectory.isDirectory) {
                Log.d("directory", testDirectory.toString())
                return it
            }
            Log.d("Path", it)
        }
        return null
    }

    private fun createFileAndSaveInExternalStorage(
        path: String
    ): File? {
        val dir = File(path, FOLDER)
        Log.d("dir", dir.toString())
        if (!dir.exists()) {
            dir.mkdir()
        }
        try {
            val file = File(dir, BACKUP)
            Log.d("fileDuringWriting", file.toString())
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
        const val FOLDER = "downloads"
        const val NO_SDCARD = "No sd-card available"

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

        private val sdCardsPaths = listOf(
            "/storage/self/primary/",
            "/storage/sdcard1",
            "/storage/extsdcard",
            "/storage/sdcard0/external_sdcard",
            "/mnt/extsdcard",
            "/mnt/sdcard/external_sd",
            "/mnt/external_sd",
            "/mnt/media_rw/sdcard1",
            "/removable/microsd",
            "/mnt/emmc",
            "/storage/external_SD",
            "/storage/ext_sd",
            "/storage/removable/sdcard1",
            "/data/sdext",
            "/data/sdext2",
            "/data/sdext3",
            "/data/sdext4"
        )


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