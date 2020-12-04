package com.example.football.ui.clubs

import androidx.lifecycle.*
import com.example.football.model.club.Club
import com.example.football.model.repo.ClubsRepository
import com.example.football.utils.livedata.mutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ClubsViewModel @Inject constructor(
    clubsRepository: ClubsRepository
) : ViewModel() {
    val clubs: LiveData<List<Club>> = clubsRepository.clubs.asLiveData()
    val club: MutableLiveData<Club?> = mutableLiveData(null)

    fun handleClubClick(club: Club) {
        this.club.value = club
    }
}