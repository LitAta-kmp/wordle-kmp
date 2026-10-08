package org.example.wordle.domain

sealed interface GameIntent {
    data class LetterTyped(val letter: Char) : GameIntent
    data object BackspacePressed : GameIntent
    data object SubmitGuess : GameIntent
    data class StartNewRound(val targetWord: String, val difficulty: Difficulty) : GameIntent
}