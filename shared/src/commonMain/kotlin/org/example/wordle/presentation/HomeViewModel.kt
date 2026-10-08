package org.example.wordle.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.example.wordle.domain.Difficulty
import org.example.wordle.domain.SettingsRepository

class HomeViewModel(
    settingsRepository: SettingsRepository
) : ViewModel() {

    val defaultDifficulty: StateFlow<Difficulty> = settingsRepository.defaultDifficulty
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Difficulty.MEDIUM
        )
}