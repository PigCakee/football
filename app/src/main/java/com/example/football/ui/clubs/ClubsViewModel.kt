package com.example.football.ui.clubs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.football.model.club.Club
import com.example.football.model.repo.ClubsRepository
import com.example.football.utils.livedata.mutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ClubsViewModel @Inject constructor(
    private val clubsRepository: ClubsRepository
) : ViewModel() {
    val clubs: MutableLiveData<List<Club>> = clubsRepository.clubs
    val club: MutableLiveData<Club> = mutableLiveData()

    init {
        if (clubs.value?.isNullOrEmpty() == true) {
            getClubs()
        }
    }

    private fun getClubs() = viewModelScope.launch(Dispatchers.IO) {
        clubsRepository.getClubs()
    }

    fun handleClubClick(club: Club) {
        this.club.value = club
    }
}