package org.example.wordle.domain

interface HistoryRepository {
    suspend fun saveRound(record: RoundRecord)
    suspend fun getAllRounds(): List<RoundRecord>
    suspend fun getRoundsByDifficulty(difficulty: Difficulty): List<RoundRecord>
    suspend fun getWinCount(difficulty: Difficulty): Int
}