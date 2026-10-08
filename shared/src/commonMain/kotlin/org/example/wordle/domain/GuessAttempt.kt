package org.example.wordle.domain

data class GuessAttempt(
    val word: String,
    val letterStates: List<LetterState>
)