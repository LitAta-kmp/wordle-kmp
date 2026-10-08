package org.example.wordle.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.wordle.domain.Difficulty
import org.example.wordle.domain.SettingsRepository

class DifficultySelectViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    fun onDifficultySelected(difficulty: Difficulty, onSaved: () -> Unit) {
        viewModelScope.launch {
            settingsRepository.setDefaultDifficulty(difficulty)
            onSaved()
        }
    }
}