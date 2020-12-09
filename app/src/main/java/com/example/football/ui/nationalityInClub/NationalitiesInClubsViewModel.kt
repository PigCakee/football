package com.example.football.ui.nationalityInClub

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.football.model.player.Player
import com.example.football.model.repo.PlayersRepository
import com.example.football.utils.livedata.mutableLiveData
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class NationalitiesInClubsViewModel @Inject constructor(
    private val playersRepository: PlayersRepository
) : ViewModel() {
    val clubs: MutableLiveData<List<String>> = mutableLiveData()
    val playersWithNationalityInClub: MutableLiveData<List<Player>> = mutableLiveData()

    init {
        getAllClubs()
    }

    fun getPlayersByNationalityInClub(nationality: String, club: String) {
        playersRepository.getPlayersByNationalityInClub(nationality, club)
            .onEach { playersWithNationalityInClub.value = it }
            .launchIn(viewModelScope)
    }

    private fun getAllClubs() {
        playersRepository.getAllClubs()
            .onEach { clubs.value = it }
            .launchIn(viewModelScope)
    }
}