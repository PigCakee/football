package com.example.football.ui.nationalityInClub

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.football.model.repo.PlayersRepository
import com.example.football.utils.livedata.mutableLiveData
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class NationalitiesInClubsViewModel @Inject constructor(
    private val playersRepository: PlayersRepository
) : ViewModel() {
    val clubs: MutableLiveData<List<String>> = mutableLiveData()
    var nationality: String? = null

    fun getAllClubs(nationality: String) {
        playersRepository.getClubsWithNationality(nationality)
            .onEach { clubs.value = it }
            .launchIn(viewModelScope)
    }
}