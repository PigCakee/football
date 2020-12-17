package com.example.football.ui.nationalities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.football.data.entity.Player
import com.example.football.data.repository.PlayersRepository
import com.example.football.utils.livedata.mutableLiveData
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class NationalitiesViewModel @Inject constructor(
    private val playersRepository: PlayersRepository
) : ViewModel() {
    val playersWithNationalityData: MutableLiveData<MutableList<Pair<List<Player>, String>>> =
        mutableLiveData()
    val nationality: MutableLiveData<String?> = mutableLiveData()

    init {
        getAllNationalities()
    }

    private fun getAllNationalities() {
        val list: MutableList<Pair<List<Player>, String>> = mutableListOf()
        playersRepository.getAllNationalities()
            .onEach { it.forEach { nationality -> getPlayersWithNationality(nationality, list) } }
            .launchIn(viewModelScope)
    }

    private fun getPlayersWithNationality(
        nationality: String,
        list: MutableList<Pair<List<Player>, String>>
    ) {
        playersRepository.getPlayersByNationality(nationality)
            .onEach {
                list.add(Pair(it, nationality))
                playersWithNationalityData.value = list
            }.launchIn(viewModelScope)
    }

    fun handleNationalityClick(nationality: String) {
        this.nationality.value = nationality
    }
}