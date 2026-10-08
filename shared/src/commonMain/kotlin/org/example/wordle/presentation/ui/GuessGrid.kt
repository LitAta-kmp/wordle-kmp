package org.example.wordle.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.wordle.domain.RoundState

@Composable
fun GuessGrid(
    state: RoundState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val wordLength = state.difficulty.wordLength
        val totalRows = state.difficulty.maxAttempts

        for (rowIndex in 0 until totalRows) {
            when {
                rowIndex < state.attempts.size -> {
                    // завершённая попытка — есть и слово, и результат сравнения
                    val attempt = state.attempts[rowIndex]
                    GuessRow(
                        word = attempt.word,
                        letterStates = attempt.letterStates,
                        wordLength = wordLength
                    )
                }
                rowIndex == state.attempts.size -> {
                    // текущая, ещё вводимая строка
                    GuessRow(
                        word = state.currentGuess,
                        letterStates = null,
                        wordLength = wordLength
                    )
                }
                else -> {
                    // будущая, ещё не начатая попытка
                    GuessRow(
                        word = "",
                        letterStates = null,
                        wordLength = wordLength
                    )
                }
            }
        }
    }
}