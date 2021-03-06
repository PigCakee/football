package com.example.football.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.football.data.entity.Player
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

@Database(entities = [Player::class], exportSchema = false, version = 3)
abstract class PlayerDatabase : RoomDatabase() {

    abstract fun dao(): PlayerDao

    private class PlayerDatabaseCallback(
        private val scope: CoroutineScope,
        private val context: Context
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.dao())
                }
            }
        }

        fun populateDatabase(dao: PlayerDao) {
            getPlayersDataFromFirebase(dao)
            //getPlayersDataFromAssets().forEach { dao.insert(it) }
        }

        fun getPlayersDataFromAssets(): List<Player> {
            return runBlocking(Dispatchers.IO) {
                val inputStream: InputStream = context.assets.open("data/players_data")
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                val string = StringBuilder()
                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    string.append(line)
                    string.append('\n')
                }
                bufferedReader.close()
                Gson().fromJson(string.toString(), object : TypeToken<List<Player>>() {}.type)
            }
        }

        // In case you want to populate firestore automatically
        fun insertDataToFirestore(list: List<Player>) {
            val remoteDatabase = FirebaseFirestore.getInstance()
            val taskData: HashMap<String, String> =
                hashMapOf("name" to "", "position" to "", "nationality" to "", "club" to "")
            list.forEach {
                taskData["name"] = it.name
                taskData["position"] = it.position
                taskData["nationality"] = it.nationality
                taskData["club"] = it.club

                remoteDatabase.collection("Players").add(taskData)
            }
        }

        fun getPlayersDataFromFirebase(dao: PlayerDao) {
            val remoteDatabase = FirebaseFirestore.getInstance()
            remoteDatabase.collection("Players")
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val list: List<Player>? = it.result?.toObjects(Player::class.java)

                        Completable.fromRunnable {
                            list?.forEach { player -> dao.insert(player) }
                        }.subscribeOn(Schedulers.io()).subscribe()
                    }
                }
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
                ).addCallback(PlayerDatabaseCallback(GlobalScope, context)).build()
                INSTANCE = instance
                instance.openHelper.setWriteAheadLoggingEnabled(false)
                instance
            }
        }
    }
}