package org.example.wordle.data

import org.example.wordle.domain.Difficulty

object WordBank {

    private val wordsByDifficulty: Map<Difficulty, List<String>> = mapOf(
        Difficulty.EASY to listOf(
            "CODE", "GAME", "WORD", "TIME", "BLUE",
            "TREE", "FISH", "STAR", "MOON", "RAIN",
            "BOOK", "DOOR", "LAMP", "SHIP", "WIND",
            "SAND", "GOLD", "BIRD", "CAKE", "LEAF"
        ),
        Difficulty.MEDIUM to listOf(
            "APPLE", "CRANE", "PAPER", "ROBOT", "HOUSE",
            "LIGHT", "SOUND", "TABLE", "PLANT", "SMILE",
            "BRAVE", "CHAIR", "DANCE", "EAGLE", "FLAME",
            "GRAPE", "HONEY", "IVORY", "JOKER", "KNIFE"
        ),
        Difficulty.HARD to listOf(
            "ORANGE", "SILVER", "MODULE", "BRANCH", "MARKET",
            "SYSTEM", "ANIMAL", "GARDEN", "WINTER", "CIRCLE",
            "BASKET", "CAMERA", "DOLLAR", "ENGINE", "FOREST",
            "GUITAR", "HAMMER", "ISLAND", "JACKET", "KITTEN"
        )
    )

    fun randomWord(difficulty: Difficulty): String {
        val words = wordsByDifficulty.getValue(difficulty)
        return words.random()
    }
}