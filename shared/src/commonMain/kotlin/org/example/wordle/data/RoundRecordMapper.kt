package org.example.wordle.data

import org.example.wordle.db.RoundHistoryEntity
import org.example.wordle.domain.Difficulty
import org.example.wordle.domain.GameStatus
import org.example.wordle.domain.RoundRecord

fun RoundHistoryEntity.toDomain(): RoundRecord {
    return RoundRecord(
        targetWord = targetWord,
        difficulty = Difficulty.valueOf(difficulty),
        status = GameStatus.valueOf(status),
        attemptsUsed = attemptsUsed.toInt(),
        playedAt = playedAt
    )
}