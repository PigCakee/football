package com.example.football.ui.nationalities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.football.model.player.Player
import com.example.football.model.repo.PlayersRepository
import com.example.football.utils.livedata.mutableLiveData
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class NationalitiesViewModel @Inject constructor(
    private val playersRepository: PlayersRepository
) : ViewModel() {
    val clubs: MutableLiveData<List<String>> = mutableLiveData()
    val playersWithNationalityData: MutableLiveData<MutableList<Pair<List<Player>, String>>> =
        mutableLiveData()
    val playersWithNationalityInClub: MutableLiveData<List<Player>> = mutableLiveData()
    val nationality: MutableLiveData<String?> = mutableLiveData()

    init {
        getAllNationalities()
    }

    private fun getAllNationalities() {
        val list: MutableList<Pair<List<Player>, String>> =
            mutableListOf()
        playersRepository.getAllNationalities().onEach {
            it.forEach { nationality ->
                getPlayersWithNationality(nationality, list)
            }
        }.launchIn(viewModelScope).invokeOnCompletion {
            playersWithNationalityData.value = list
        }
    }

    private fun getPlayersWithNationality(nationality: String, list: MutableList<Pair<List<Player>, String>>) {
        playersRepository.getPlayersByNationality(nationality).onEach {
            list.add(Pair(it, nationality))
        }.launchIn(viewModelScope)
    }

    fun getPlayersByNationalityInClub(nationality: String, club: String) {
        playersRepository.getPlayersByNationalityInClub(nationality, club)
            .onEach { playersWithNationalityInClub.value = it }.launchIn(viewModelScope)
    }

    fun getAllClubs() {
        playersRepository.getAllClubs().onEach { clubs.value = it }.launchIn(viewModelScope)
    }

    fun handleNationalityClick(nationality: String) {
        this.nationality.value = nationality
    }
}