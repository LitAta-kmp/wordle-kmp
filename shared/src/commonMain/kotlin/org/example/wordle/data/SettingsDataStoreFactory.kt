package org.example.wordle.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

expect class SettingsDataStoreFactory {
    fun create(): DataStore<Preferences>
}