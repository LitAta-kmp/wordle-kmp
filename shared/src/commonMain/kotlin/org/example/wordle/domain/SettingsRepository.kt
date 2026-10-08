package org.example.wordle.domain

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val defaultDifficulty: Flow<Difficulty>
    suspend fun setDefaultDifficulty(difficulty: Difficulty)
}