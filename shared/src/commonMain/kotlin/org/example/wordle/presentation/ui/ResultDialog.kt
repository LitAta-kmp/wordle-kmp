package org.example.wordle.presentation.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.example.wordle.domain.GameStatus

@Composable
fun ResultDialog(
    status: GameStatus,
    targetWord: String,
    onPlayAgainClick: () -> Unit
) {
    val title = if (status == GameStatus.WON) "You won!" else "You lost"
    val message = if (status == GameStatus.WON) {
        "Great job guessing the word."
    } else {
        "The word was: $targetWord"
    }

    AlertDialog(
        onDismissRequest = { /* закрытие только через кнопку, не по клику вне диалога */ },
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            Button(onClick = onPlayAgainClick) {
                Text("Play again")
            }
        }
    )
}