package org.example.wordle.domain

import kotlinx.serialization.Serializable

@Serializable
enum class Difficulty(val wordLength: Int, val maxAttempts: Int) {
    EASY(4, 6),
    MEDIUM(5, 6),
    HARD(6, 6)
}