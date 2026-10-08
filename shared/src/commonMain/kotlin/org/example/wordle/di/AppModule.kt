package org.example.wordle.di

import org.example.wordle.data.DataStoreSettingsRepository
import org.example.wordle.data.SqlDelightHistoryRepository
import org.example.wordle.db.WordleDatabase
import org.example.wordle.domain.Difficulty
import org.example.wordle.domain.HistoryRepository
import org.example.wordle.domain.SettingsRepository
import org.example.wordle.presentation.DifficultySelectViewModel
import org.example.wordle.presentation.GameViewModel
import org.example.wordle.presentation.HistoryViewModel
import org.example.wordle.presentation.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val sharedModule = module {
    single { WordleDatabase(get()) }
    single<HistoryRepository> { SqlDelightHistoryRepository(get()) }
    single<SettingsRepository> { DataStoreSettingsRepository(get()) }
    viewModel { (difficulty: Difficulty) -> GameViewModel(get(), difficulty) }
    viewModel { HistoryViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { DifficultySelectViewModel(get()) }
}