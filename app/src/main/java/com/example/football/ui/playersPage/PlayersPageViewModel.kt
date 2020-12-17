package com.example.football.ui.playersPage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.football.data.entity.Player
import com.example.football.data.repository.PlayersRepository
import com.example.football.utils.livedata.mutableLiveData
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PlayersPageViewModel @Inject constructor(
    private val playersRepository: PlayersRepository
) : ViewModel() {
    val playersOnPositionInClub: MutableLiveData<List<Player>> = mutableLiveData()
    val playersWithNationalityInClub: MutableLiveData<List<Player>> = mutableLiveData()
    val playersWithNationalityInPosition: MutableLiveData<List<Player>> = mutableLiveData()

    fun getPlayersByPositionInClub(position: String, club: String) {
        playersRepository.getPlayersByPositionInClub(position, club)
            .onEach { playersOnPositionInClub.value = it }
            .launchIn(viewModelScope)
    }

    fun getPlayersByNationalityInClub(nationality: String, club: String) {
        playersRepository.getPlayersByNationalityInClub(nationality, club)
            .onEach { playersWithNationalityInClub.value = it }
            .launchIn(viewModelScope)
    }

    fun getPlayersWithNationalityInPosition(nationality: String, position: String) {
        playersRepository.getPlayersWithNationalityInPosition(nationality, position)
            .onEach { playersWithNationalityInPosition.value = it }
            .launchIn(viewModelScope)
    }
}
