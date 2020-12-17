package com.example.football.ui.positionInClub

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.football.data.repository.PlayersRepository
import com.example.football.utils.livedata.mutableLiveData
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PositionFilterViewModel @Inject constructor(
    private val playersRepository: PlayersRepository
) : ViewModel() {
    val clubs: MutableLiveData<List<String>> = mutableLiveData()
    val nationalities: MutableLiveData<List<String>> = mutableLiveData()
    var position: String? = null
    var title: String = ""

    fun getClubsWithPosition(position: String) {
        playersRepository.getClubsWithPosition(position)
            .onEach { clubs.value = it }
            .launchIn(viewModelScope)
    }

    fun getNationalitiesWithPosition(position: String) {
        playersRepository.getNationalitiesWithPosition(position)
            .onEach { nationalities.value = it }
            .launchIn(viewModelScope)
    }
}