package com.example.football.ui.positions

import androidx.lifecycle.*
import com.example.football.model.club.Club
import com.example.football.model.repo.ClubsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PositionsViewModel @Inject constructor(
    private val clubsRepository: ClubsRepository
) : ViewModel() {
    val clubs: LiveData<List<Club>> = clubsRepository.clubs.asLiveData()

    fun handlePositionClick(position: String) {

    }
}