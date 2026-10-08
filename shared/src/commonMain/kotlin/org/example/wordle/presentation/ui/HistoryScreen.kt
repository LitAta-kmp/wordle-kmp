package org.example.wordle.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.wordle.domain.GameStatus
import org.example.wordle.domain.RoundRecord
import org.example.wordle.presentation.HistoryViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HistoryScreen(
    onBackClick: () -> Unit,
    viewModel: HistoryViewModel = koinViewModel()
) {
    val rounds by viewModel.rounds.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("History") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Text("←")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (rounds.isEmpty()) {
            Text("No games played yet", modifier = Modifier.padding(innerPadding).padding(16.dp))
            return@Scaffold
        }

        LazyColumn(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            items(rounds) { round ->
                RoundRow(round)
            }
        }
    }
}

@Composable
private fun RoundRow(round: RoundRecord) {
    Column(modifier = Modifier.padding(16.dp)) {
        val resultLabel = if (round.status == GameStatus.WON) "Won" else "Lost"
        Text("$resultLabel — ${round.targetWord} (${round.difficulty.name})")
        Text("Attempts used: ${round.attemptsUsed}")
    }
}