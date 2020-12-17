package com.example.football.ui.clubPosition

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.football.data.repository.PlayersRepository
import com.example.football.utils.livedata.mutableLiveData
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ClubFilterViewModel @Inject constructor(
    private val playersRepository: PlayersRepository
) : ViewModel() {
    val positions: MutableLiveData<List<String>> = mutableLiveData()
    val nationalities: MutableLiveData<List<String>> = mutableLiveData()
    var club: String? = null
    var title: String = ""

    fun getPositionsInClub(club: String) {
        playersRepository.getPositionsInClub(club)
            .onEach { positions.value = it }
            .launchIn(viewModelScope)
    }

    fun getNationalitiesInClub(club: String) {
        playersRepository.getNationalitiesInCLub(club)
            .onEach { nationalities.value = it }
            .launchIn(viewModelScope)
    }
}