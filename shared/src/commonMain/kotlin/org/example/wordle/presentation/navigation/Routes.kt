package org.example.wordle.presentation.navigation

import kotlinx.serialization.Serializable
import org.example.wordle.domain.Difficulty

@Serializable
sealed interface Routes {
    @Serializable
    data object Home : Routes
    @Serializable
    data object History : Routes
    @Serializable
    data object DifficultySelect : Routes

    @Serializable
    data class Game(val difficulty: Difficulty) : Routes
}
