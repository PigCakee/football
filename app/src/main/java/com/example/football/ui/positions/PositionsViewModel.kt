package com.example.football.ui.positions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.football.model.club.Club
import com.example.football.model.repo.ClubsRepository
import com.example.football.utils.livedata.mutableLiveData
import javax.inject.Inject

class PositionsViewModel @Inject constructor(
    clubsRepository: ClubsRepository
) : ViewModel() {
    val clubs: LiveData<List<Club>> = clubsRepository.clubs.asLiveData()
    val position: MutableLiveData<String?> = mutableLiveData()

    fun handlePositionClick(position: String) {
        this.position.value = position
    }
}