package org.example.wordle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.example.wordle.data.WordBank
import org.example.wordle.domain.Difficulty
import org.example.wordle.domain.GameIntent
import org.example.wordle.domain.GameStatus
import org.example.wordle.presentation.GameViewModel
import org.example.wordle.presentation.navigation.Routes
import org.example.wordle.presentation.ui.DifficultySelectScreen
import org.example.wordle.presentation.ui.GuessGrid
import org.example.wordle.presentation.ui.HistoryScreen
import org.example.wordle.presentation.ui.HomeScreen
import org.example.wordle.presentation.ui.Keyboard
import org.example.wordle.presentation.ui.ResultDialog
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun App() {
    MaterialTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
        ) {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Routes.Home) {
                composable<Routes.Home> {
                    HomeScreen(
                        onPlayClick = { difficulty -> navController.navigate(Routes.Game(difficulty)) },
                        onChangeDifficultyClick = { navController.navigate(Routes.DifficultySelect) },
                        onHistoryClick = { navController.navigate(Routes.History) }
                    )
                }
                composable<Routes.DifficultySelect> {
                    DifficultySelectScreen(
                        onDifficultySelected = { difficulty ->
                            navController.navigate(Routes.Game(difficulty)) {
                                popUpTo(Routes.Home)
                            }
                        }
                    )
                }
                composable<Routes.Game> { backStackEntry ->
                    val route: Routes.Game = backStackEntry.toRoute()
                    GameScreen(initialDifficulty = route.difficulty)
                }
                composable<Routes.History> {
                    HistoryScreen(onBackClick = { navController.popBackStack() })
                }
            }
        }
    }
}

@Composable
fun GameScreen(
    initialDifficulty: Difficulty,
    viewModel: GameViewModel = koinViewModel(
        parameters = { parametersOf(initialDifficulty) }
    )
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "WORDLE",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            letterSpacing = 4.sp,
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        )
        Text(
            text = "Guess the word in ${state.difficulty.maxAttempts} tries",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "${state.difficulty.name} · ${state.attemptsLeft} attempts left",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            GuessGrid(state = state)
        }

        Keyboard(
            attempts = state.attempts,
            onLetterClick = { letter -> viewModel.onIntent(GameIntent.LetterTyped(letter)) },
            onBackspaceClick = { viewModel.onIntent(GameIntent.BackspacePressed) },
            onEnterClick = { viewModel.onIntent(GameIntent.SubmitGuess) }
        )
    }

    if (state.status != GameStatus.IN_PROGRESS) {
        ResultDialog(
            status = state.status,
            targetWord = state.targetWord,
            onPlayAgainClick = {
                val newWord = WordBank.randomWord(state.difficulty)
                viewModel.onIntent(
                    GameIntent.StartNewRound(targetWord = newWord, difficulty = state.difficulty)
                )
            }
        )
    }
}