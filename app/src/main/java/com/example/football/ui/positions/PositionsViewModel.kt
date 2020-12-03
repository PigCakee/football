package com.example.football.ui.positions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.football.model.club.Club
import com.example.football.model.repo.ClubsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PositionsViewModel @Inject constructor(
    private val clubsRepository: ClubsRepository
) : ViewModel() {
    val clubs: MutableLiveData<List<Club>> = clubsRepository.clubs

    init {
        if (clubs.value?.isNullOrEmpty() == true) {
            getClubs()
        }
    }

    private fun getClubs() = viewModelScope.launch(Dispatchers.IO) {
        clubsRepository.getClubs()
    }

    fun handlePositionClick(position: String) {

    }
}