package org.example.wordle.di

import app.cash.sqldelight.db.SqlDriver
import org.example.wordle.data.DatabaseDriverFactory
import org.koin.dsl.module
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.example.wordle.data.SettingsDataStoreFactory

val platformModule = module {
    single<SqlDriver> { DatabaseDriverFactory(get()).createDriver() }
    single<DataStore<Preferences>> { SettingsDataStoreFactory(get()).create() }
}