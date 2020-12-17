package com.example.football.ui.clubs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.football.data.entity.Player
import com.example.football.data.repository.PlayersRepository
import com.example.football.utils.livedata.mutableLiveData
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ClubsViewModel @Inject constructor(
    private val playersRepository: PlayersRepository
) : ViewModel() {
    val playersInClub: MutableLiveData<MutableList<Pair<List<Player>, String>>> = mutableLiveData()
    val club: MutableLiveData<String?> = mutableLiveData(null)

    init {
        getClubs()
    }

    private fun getClubs() {
        val list: MutableList<Pair<List<Player>, String>> = mutableListOf()
        playersRepository.getAllClubs()
            .onEach { it.forEach { club -> getPlayersByClub(club, list) } }
            .launchIn(viewModelScope)
    }

    private fun getPlayersByClub(club: String, list: MutableList<Pair<List<Player>, String>>) {
        playersRepository.getPlayersByClub(club)
            .onEach {
                list.add(Pair(it, club))
                playersInClub.value = list
            }.launchIn(viewModelScope)
    }

    fun handleClubClick(club: String) {
        this.club.value = club
    }
}