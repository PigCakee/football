package com.example.football.ui.clubs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.football.model.club.Player
import com.example.football.model.repo.PlayersRepository
import com.example.football.utils.livedata.mutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class ClubsViewModel @Inject constructor(
    private val playersRepository: PlayersRepository
) : ViewModel() {
    val clubs: MutableLiveData<List<String>> = mutableLiveData()
    val playersInClub: MutableLiveData<Pair<List<Player>, String>> = mutableLiveData()
    val positions: MutableLiveData<List<String>> = mutableLiveData()
    val playersOnPositionInClub: MutableLiveData<List<Player>> = mutableLiveData()
    val club: MutableLiveData<String?> = mutableLiveData(null)

    fun getClubs() = viewModelScope.launch(Dispatchers.IO) {
        playersRepository.getAllClubs().onEach { clubs.postValue(it) }
    }

    fun getPlayersByClub(club: String) = viewModelScope.launch(Dispatchers.IO) {
        playersRepository.getPlayersByClub(club).onEach { playersInClub.postValue(Pair(it, club)) }
    }

    fun getAllPositions() = viewModelScope.launch(Dispatchers.IO) {
        playersRepository.getAllPositions().onEach { positions.postValue(it) }
    }

    fun getPlayersByPositionInClub(position: String, club: String) =
        viewModelScope.launch(Dispatchers.IO) {
            playersRepository.getPlayersByPositionInClub(position, club)
                .onEach { playersOnPositionInClub.postValue(it) }
        }

    fun handleClubClick(club: String) {
        this.club.value = club
    }
}