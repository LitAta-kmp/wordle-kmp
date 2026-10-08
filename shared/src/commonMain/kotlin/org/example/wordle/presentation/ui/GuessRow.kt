package org.example.wordle.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.wordle.domain.LetterState

@Composable
fun GuessRow(
    word: String,
    letterStates: List<LetterState>?,
    wordLength: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        for (i in 0 until wordLength) {
            val letter = word.getOrNull(i)
            val state = letterStates?.getOrNull(i)
            LetterTile(letter = letter, state = state)
        }
    }
}