package org.example.wordle.domain

data class RoundRecord(
    val targetWord: String,
    val difficulty: Difficulty,
    val status: GameStatus,
    val attemptsUsed: Int,
    val playedAt: Long
)