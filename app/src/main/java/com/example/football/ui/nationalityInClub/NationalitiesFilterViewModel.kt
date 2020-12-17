package com.example.football.ui.nationalityInClub

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.football.data.repository.PlayersRepository
import com.example.football.utils.livedata.mutableLiveData
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class NationalitiesFilterViewModel @Inject constructor(
    private val playersRepository: PlayersRepository
) : ViewModel() {
    val clubs: MutableLiveData<List<String>> = mutableLiveData()
    val positions: MutableLiveData<List<String>> = mutableLiveData()
    var nationality: String? = null
    var title: String = ""

    fun getClubsWithNationality(nationality: String) {
        playersRepository.getClubsWithNationality(nationality)
            .onEach { clubs.value = it }
            .launchIn(viewModelScope)
    }

    fun getPositionsWithNationality(nationality: String) {
        playersRepository.getPositionsWithNationality(nationality)
            .onEach { positions.value = it }
            .launchIn(viewModelScope)
    }
}