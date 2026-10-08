package org.example.wordle.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.wordle.data.WordBank
import org.example.wordle.domain.Difficulty
import org.example.wordle.domain.GameIntent
import org.example.wordle.domain.GameReducer
import org.example.wordle.domain.GameStatus
import org.example.wordle.domain.HistoryRepository
import org.example.wordle.domain.RoundRecord
import org.example.wordle.domain.RoundState
import kotlinx.datetime.Clock

class GameViewModel(
    private val historyRepository: HistoryRepository,
    initialDifficulty: Difficulty = Difficulty.MEDIUM
) : ViewModel() {

    private val _state = MutableStateFlow(createNewRoundState(initialDifficulty))
    val state: StateFlow<RoundState> = _state.asStateFlow()

    fun onIntent(intent: GameIntent) {
        viewModelScope.launch {
            val previousState = _state.value
            val newState = GameReducer.reduce(previousState, intent)
            _state.value = newState

            val justFinished = previousState.status == GameStatus.IN_PROGRESS &&
                    newState.status != GameStatus.IN_PROGRESS
            if (justFinished) {
                saveRoundResult(newState)
            }
        }
    }

    private suspend fun saveRoundResult(state: RoundState) {
        historyRepository.saveRound(
            RoundRecord(
                targetWord = state.targetWord,
                difficulty = state.difficulty,
                status = state.status,
                attemptsUsed = state.attempts.size,
                playedAt = Clock.System.now().toEpochMilliseconds()
            )
        )
    }

    private fun createNewRoundState(difficulty: Difficulty): RoundState {
        val targetWord = WordBank.randomWord(difficulty)
        return RoundState(targetWord = targetWord.uppercase(), difficulty = difficulty)
    }
}