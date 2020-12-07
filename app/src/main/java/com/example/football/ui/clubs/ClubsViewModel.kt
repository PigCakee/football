package com.example.football.ui.clubs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.football.model.club.Club
import com.example.football.model.repo.ClubsRepository
import com.example.football.utils.livedata.mutableLiveData
import javax.inject.Inject

class ClubsViewModel @Inject constructor(
    private val clubsRepository: ClubsRepository
) : ViewModel() {
    val clubs: LiveData<List<Club>> = clubsRepository.clubs.asLiveData()
    val club: MutableLiveData<Club?> = mutableLiveData(null)

    fun handleClubClick(club: Club) {
        this.club.value = club
    }
}