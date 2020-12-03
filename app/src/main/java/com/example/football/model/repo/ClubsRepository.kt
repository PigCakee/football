package com.example.football.model.repo

import androidx.lifecycle.MutableLiveData
import com.example.football.model.club.Club
import com.example.football.utils.livedata.mutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ClubsRepository @Inject constructor() {
    val clubs: MutableLiveData<List<Club>> =
        mutableLiveData()

    suspend fun getClubs() {
        // сразу же представим, что тянем данные из БД или из интернета
        withContext(Dispatchers.IO) {
            clubs.postValue(clubsData)
        }
    }

    companion object {
        private val clubsData = listOf<Club>(
            Club("Bavaria", listOf()),
            Club("PSG", listOf()),
            Club("Juventus", listOf()),
            Club("Tottanham", listOf())
        )
    }
}