package org.example.wordle.domain

object WordComparator {

    fun compare(guess: String, target: String): List<LetterState> {
        val guessUpper = guess.uppercase()
        val targetUpper = target.uppercase()

        val result = MutableList(guessUpper.length) { LetterState.ABSENT }

        // счётчик оставшихся (ещё не сопоставленных) букв в загаданном слове
        val remainingLetters = mutableMapOf<Char, Int>()
        for (char in targetUpper) {
            remainingLetters[char] = (remainingLetters[char] ?: 0) + 1
        }

        // проход 1: точные совпадения по позиции
        for (i in guessUpper.indices) {
            if (guessUpper[i] == targetUpper[i]) {
                result[i] = LetterState.CORRECT
                remainingLetters[guessUpper[i]] = remainingLetters.getValue(guessUpper[i]) - 1
            }
        }

        // проход 2: частичные совпадения (буква есть, но не на своём месте)
        for (i in guessUpper.indices) {
            if (result[i] == LetterState.CORRECT) continue // уже обработано

            val char = guessUpper[i]
            val remaining = remainingLetters[char] ?: 0
            if (remaining > 0) {
                result[i] = LetterState.PRESENT
                remainingLetters[char] = remaining - 1
            }
            // иначе остаётся ABSENT (значение по умолчанию)
        }

        return result
    }
}