package com.example.football.ui.positions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.football.model.club.Player
import com.example.football.model.repo.ClubsRepository
import com.example.football.utils.livedata.mutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class PositionsViewModel @Inject constructor(
    private val clubsRepository: ClubsRepository
) : ViewModel() {
    val positions: MutableLiveData<List<String>> = mutableLiveData()
    val clubs: MutableLiveData<List<String>> = mutableLiveData()
    val playersOnPositions: MutableLiveData<Pair<List<Player>, String>> = mutableLiveData()
    val playersOnPositionInClub: MutableLiveData<List<Player>> = mutableLiveData()
    val position: MutableLiveData<String?> = mutableLiveData()

    fun getPositions() = viewModelScope.launch(Dispatchers.IO) {
        clubsRepository.getAllPositions().onEach { positions.value = it }
    }

    fun getPlayersByPosition(position: String) = viewModelScope.launch(Dispatchers.IO) {
        clubsRepository.getPlayersByPosition(position)
            .onEach { playersOnPositions.value = Pair(it, position) }
    }

    fun getAllClubs() = viewModelScope.launch(Dispatchers.IO) {
        clubsRepository.getAllClubs().onEach { clubs.value = it }
    }

    fun getPlayersByPositionInClub(position: String, club: String) =
        viewModelScope.launch(Dispatchers.IO) {
            clubsRepository.getPlayersByPositionInClub(position, club)
                .onEach { playersOnPositionInClub.value = it }
        }

    fun handlePositionClick(position: String) {
        this.position.value = position
    }
}