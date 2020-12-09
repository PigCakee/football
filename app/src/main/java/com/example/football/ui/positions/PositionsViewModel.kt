package com.example.football.ui.positions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.football.model.player.Player
import com.example.football.model.repo.PlayersRepository
import com.example.football.utils.livedata.mutableLiveData
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PositionsViewModel @Inject constructor(
    private val playersRepository: PlayersRepository
) : ViewModel() {
    val playersOnPositions: MutableLiveData<MutableList<Pair<List<Player>, String>>> =
        mutableLiveData()
    val position: MutableLiveData<String?> = mutableLiveData()

    init {
        getPositions()
    }

    private fun getPositions() {
        val list: MutableList<Pair<List<Player>, String>> = mutableListOf()
        playersRepository.getAllPositions()
            .onEach { it.forEach { position -> getPlayersByPosition(position, list) } }
            .onCompletion { playersOnPositions.value = list }
            .launchIn(viewModelScope)
    }

    private fun getPlayersByPosition(
        position: String,
        list: MutableList<Pair<List<Player>, String>>
    ) {
        playersRepository.getPlayersByPosition(position)
            .onEach { list.add(Pair(it, position)) }
            .launchIn(viewModelScope)
    }

    fun handlePositionClick(position: String) {
        this.position.value = position
    }
}