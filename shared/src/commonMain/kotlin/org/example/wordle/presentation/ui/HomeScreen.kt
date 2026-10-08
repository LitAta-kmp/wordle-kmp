package org.example.wordle.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.wordle.domain.Difficulty
import org.example.wordle.presentation.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    onPlayClick: (Difficulty) -> Unit,
    onChangeDifficultyClick: () -> Unit,
    onHistoryClick: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val difficulty by viewModel.defaultDifficulty.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Wordle")
        Text(
            text = "Guess the hidden word in ${difficulty.maxAttempts} tries",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
        )
        Text("Difficulty: ${difficulty.name}")
        Button(onClick = { onPlayClick(difficulty) }) {
            Text("Play")
        }
        Button(onClick = onChangeDifficultyClick) {
            Text("Change difficulty")
        }
        Button(onClick = onHistoryClick) {
            Text("History")
        }
    }
}