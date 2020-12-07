package com.example.football.ui.positions

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

class PositionsViewModel @Inject constructor(
    private val playersRepository: PlayersRepository
) : ViewModel() {
    val positions: MutableLiveData<List<String>> = mutableLiveData()
    val clubs: MutableLiveData<List<String>> = mutableLiveData()
    val playersOnPositions: MutableLiveData<Pair<List<Player>, String>> = mutableLiveData()
    val playersOnPositionInClub: MutableLiveData<List<Player>> = mutableLiveData()
    val position: MutableLiveData<String?> = mutableLiveData()

    fun getPositions() = viewModelScope.launch(Dispatchers.IO) {
        playersRepository.getAllPositions().onEach { positions.postValue(it) }
    }

    fun getPlayersByPosition(position: String) = viewModelScope.launch(Dispatchers.IO) {
        playersRepository.getPlayersByPosition(position)
            .onEach { playersOnPositions.postValue(Pair(it, position)) }
    }

    fun getAllClubs() = viewModelScope.launch(Dispatchers.IO) {
        playersRepository.getAllClubs().onEach { clubs.postValue(it) }
    }

    fun getPlayersByPositionInClub(position: String, club: String) =
        viewModelScope.launch(Dispatchers.IO) {
            playersRepository.getPlayersByPositionInClub(position, club)
                .onEach { playersOnPositionInClub.postValue(it) }
        }

    fun handlePositionClick(position: String) {
        this.position.value = position
    }
}