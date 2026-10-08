package org.example.wordle.domain

data class RoundState(
    val targetWord: String,
    val difficulty: Difficulty,
    val attempts: List<GuessAttempt> = emptyList(),
    val currentGuess: String = "",
    val status: GameStatus = GameStatus.IN_PROGRESS
) {
    val attemptsLeft: Int
        get() = difficulty.maxAttempts - attempts.size
}