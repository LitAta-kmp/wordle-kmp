package org.example.wordle.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.wordle.domain.GuessAttempt
import org.example.wordle.domain.LetterState

private val KEYBOARD_ROWS = listOf(
    "QWERTYUIOP",
    "ASDFGHJKL",
    "ZXCVBNM"
)

@Composable
fun Keyboard(
    attempts: List<GuessAttempt>,
    onLetterClick: (Char) -> Unit,
    onBackspaceClick: () -> Unit,
    onEnterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val letterStates = computeKeyStates(attempts)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        for ((rowIndex, row) in KEYBOARD_ROWS.withIndex()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (rowIndex == 2) {
                    ActionKey(
                        label = "ENTER",
                        modifier = Modifier.weight(1.5f),
                        onClick = onEnterClick
                    )
                }
                for (letter in row) {
                    KeyboardKey(
                        letter = letter,
                        state = letterStates[letter],
                        modifier = Modifier.weight(1f),
                        onClick = { onLetterClick(letter) }
                    )
                }
                if (rowIndex == 2) {
                    ActionKey(
                        label = "⌫",
                        modifier = Modifier.weight(1.5f),
                        onClick = onBackspaceClick
                    )
                }
            }
        }
    }
}

@Composable
private fun KeyboardKey(
    letter: Char,
    state: LetterState?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val backgroundColor = when (state) {
        LetterState.CORRECT -> Color(0xFF6AAA64)
        LetterState.PRESENT -> Color(0xFFC9B458)
        LetterState.ABSENT -> Color(0xFF787C7E)
        null -> Color(0xFFD3D6DA)
    }
    val textColor = if (state == null) Color.Black else Color.White

    Box(
        modifier = modifier
            .height(52.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = letter.toString(), color = textColor, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
    }
}

@Composable
private fun ActionKey(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(52.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFFD3D6DA))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, color = Color.Black, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
    }
}

private fun computeKeyStates(attempts: List<GuessAttempt>): Map<Char, LetterState> {
    val result = mutableMapOf<Char, LetterState>()

    for (attempt in attempts) {
        for (i in attempt.word.indices) {
            val letter = attempt.word[i]
            val newState = attempt.letterStates[i]
            val currentBest = result[letter]

            val shouldUpdate = when {
                currentBest == null -> true
                currentBest == LetterState.CORRECT -> false
                newState == LetterState.CORRECT -> true
                currentBest == LetterState.PRESENT -> false
                newState == LetterState.PRESENT -> true
                else -> false
            }

            if (shouldUpdate) {
                result[letter] = newState
            }
        }
    }

    return result
}