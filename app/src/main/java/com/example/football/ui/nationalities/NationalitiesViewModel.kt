package com.example.football.ui.nationalities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.football.model.club.Club
import com.example.football.model.repo.ClubsRepository
import com.example.football.utils.livedata.mutableLiveData
import javax.inject.Inject

class NationalitiesViewModel @Inject constructor(
    clubsRepository: ClubsRepository
) : ViewModel() {
    val clubs: LiveData<List<Club>> = clubsRepository.clubs.asLiveData()
    val nationality: MutableLiveData<String?> = mutableLiveData()

    fun handleNationalityClick(nationality: String) {
        this.nationality.value = nationality
    }
}