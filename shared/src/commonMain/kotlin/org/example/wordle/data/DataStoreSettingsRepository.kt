package org.example.wordle.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.wordle.domain.Difficulty
import org.example.wordle.domain.SettingsRepository

private val DEFAULT_DIFFICULTY_KEY = stringPreferencesKey("default_difficulty")

class DataStoreSettingsRepository(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    override val defaultDifficulty: Flow<Difficulty> = dataStore.data.map { preferences ->
        val storedName = preferences[DEFAULT_DIFFICULTY_KEY]
        storedName?.let { Difficulty.valueOf(it) } ?: Difficulty.MEDIUM
    }

    override suspend fun setDefaultDifficulty(difficulty: Difficulty) {
        dataStore.edit { preferences ->
            preferences[DEFAULT_DIFFICULTY_KEY] = difficulty.name
        }
    }
}