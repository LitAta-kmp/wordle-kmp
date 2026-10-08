package org.example.wordle.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.wordle.domain.HistoryRepository
import org.example.wordle.domain.RoundRecord

class HistoryViewModel(
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val _rounds = MutableStateFlow<List<RoundRecord>>(emptyList())
    val rounds: StateFlow<List<RoundRecord>> = _rounds.asStateFlow()

    init {
        loadRounds()
    }

    private fun loadRounds() {
        viewModelScope.launch {
            _rounds.value = historyRepository.getAllRounds()
        }
    }
}