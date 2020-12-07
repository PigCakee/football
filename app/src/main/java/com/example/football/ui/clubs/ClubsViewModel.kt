package com.example.football.ui.clubs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.football.model.player.Player
import com.example.football.model.repo.PlayersRepository
import com.example.football.utils.livedata.mutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ClubsViewModel @Inject constructor(
    private val playersRepository: PlayersRepository
) : ViewModel() {
    val playersInClub: MutableLiveData<Pair<List<Player>, String>> = mutableLiveData()
    val positions: MutableLiveData<List<String>> = mutableLiveData()
    val playersOnPositionInClub: MutableLiveData<List<Player>> = mutableLiveData()
    val club: MutableLiveData<String?> = mutableLiveData(null)

    fun getClubs() = viewModelScope.launch(Dispatchers.IO) {
        playersRepository.getAllClubs().collect {
            it.forEach { club ->
                getPlayersByClub(club)
                delay(1000L)
            }
        }
    }

    private fun getPlayersByClub(club: String) = viewModelScope.launch(Dispatchers.IO) {
        playersRepository.getPlayersByClub(club).collect { playersInClub.postValue(Pair(it, club)) }
    }

    fun getAllPositions() = viewModelScope.launch(Dispatchers.IO) {
        playersRepository.getAllPositions().collect { positions.postValue(it) }
    }

    fun getPlayersByPositionInClub(position: String, club: String) =
        viewModelScope.launch(Dispatchers.IO) {
            playersRepository.getPlayersByPositionInClub(position, club)
                .collect { playersOnPositionInClub.postValue(it) }
        }

    fun handleClubClick(club: String) {
        this.club.value = club
    }
}