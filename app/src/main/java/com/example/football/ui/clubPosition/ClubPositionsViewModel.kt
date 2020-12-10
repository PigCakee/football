package com.example.football.ui.clubPosition

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.football.model.player.Player
import com.example.football.model.repo.PlayersRepository
import com.example.football.utils.livedata.mutableLiveData
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ClubPositionsViewModel @Inject constructor(
    private val playersRepository: PlayersRepository
) : ViewModel() {
    val positions: MutableLiveData<List<String>> = mutableLiveData()

    init {
        getAllPositions()
    }

    private fun getAllPositions() {
        playersRepository.getAllPositions()
            .onEach { positions.value = it }
            .launchIn(viewModelScope)
    }
}