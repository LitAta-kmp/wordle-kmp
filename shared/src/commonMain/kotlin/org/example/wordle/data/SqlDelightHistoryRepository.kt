package org.example.wordle.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.wordle.db.WordleDatabase
import org.example.wordle.domain.Difficulty
import org.example.wordle.domain.HistoryRepository
import org.example.wordle.domain.RoundRecord

class SqlDelightHistoryRepository(
    private val database: WordleDatabase
) : HistoryRepository {

    private val queries = database.roundHistoryQueries

    override suspend fun saveRound(record: RoundRecord) {
        withContext(Dispatchers.Default) {
            queries.insertRound(
                targetWord = record.targetWord,
                difficulty = record.difficulty.name,
                status = record.status.name,
                attemptsUsed = record.attemptsUsed.toLong(),
                playedAt = record.playedAt
            )
        }
    }

    override suspend fun getAllRounds(): List<RoundRecord> {
        return withContext(Dispatchers.Default) {
            queries.selectAllRounds().executeAsList().map { it.toDomain() }
        }
    }

    override suspend fun getRoundsByDifficulty(difficulty: Difficulty): List<RoundRecord> {
        return withContext(Dispatchers.Default) {
            queries.selectRoundsByDifficulty(difficulty.name).executeAsList().map { it.toDomain() }
        }
    }

    override suspend fun getWinCount(difficulty: Difficulty): Int {
        return withContext(Dispatchers.Default) {
            queries.countWinsByDifficulty(difficulty.name).executeAsOne().toInt()
        }
    }
}