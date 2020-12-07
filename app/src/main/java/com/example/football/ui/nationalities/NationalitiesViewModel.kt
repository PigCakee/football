package com.example.football.ui.nationalities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.football.model.player.Player
import com.example.football.model.repo.PlayersRepository
import com.example.football.utils.livedata.mutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class NationalitiesViewModel @Inject constructor(
    private val playersRepository: PlayersRepository
) : ViewModel() {
    val nationalities: MutableLiveData<List<String>> = mutableLiveData()
    val clubs: MutableLiveData<List<String>> = mutableLiveData()
    val playersWithNationality: MutableLiveData<Pair<List<Player>, String>> = mutableLiveData()
    val playersWithNationalityInClub: MutableLiveData<List<Player>> = mutableLiveData()
    val nationality: MutableLiveData<String?> = mutableLiveData()

    fun getAllNationalities() = viewModelScope.launch(Dispatchers.IO) {
        playersRepository.getAllNationalities().collect {
            nationalities.postValue(it)
        }
    }

    fun getPlayersWithNationality(nationality: String) = viewModelScope.launch(Dispatchers.IO) {
        playersRepository.getPlayersByNationality(nationality).collect {
            playersWithNationality.postValue(
                Pair(it, nationality)
            )
        }
    }

    fun getPlayersByNationalityInClub(nationality: String, club: String) =
        viewModelScope.launch(Dispatchers.IO) {
            playersRepository.getPlayersByNationalityInClub(nationality, club)
                .collect { playersWithNationalityInClub.postValue(it) }
        }

    fun getAllClubs() = viewModelScope.launch(Dispatchers.IO) {
        playersRepository.getAllClubs().collect { clubs.postValue(it) }
    }

    fun handleNationalityClick(nationality: String) {
        this.nationality.value = nationality
    }
}