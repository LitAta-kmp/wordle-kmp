package org.example.wordle.domain

object GameReducer {

    fun reduce(state: RoundState, intent: GameIntent): RoundState {
        return when (intent) {
            is GameIntent.LetterTyped -> onLetterTyped(state, intent.letter)
            is GameIntent.BackspacePressed -> onBackspace(state)
            is GameIntent.SubmitGuess -> onSubmitGuess(state)
            is GameIntent.StartNewRound -> onStartNewRound(intent.targetWord, intent.difficulty)
        }
    }

    private fun onLetterTyped(state: RoundState, letter: Char): RoundState {
        if (state.status != GameStatus.IN_PROGRESS) return state
        if (state.currentGuess.length >= state.difficulty.wordLength) return state

        return state.copy(currentGuess = state.currentGuess + letter.uppercaseChar())
    }

    private fun onBackspace(state: RoundState): RoundState {
        if (state.status != GameStatus.IN_PROGRESS) return state
        if (state.currentGuess.isEmpty()) return state

        return state.copy(currentGuess = state.currentGuess.dropLast(1))
    }

    private fun onSubmitGuess(state: RoundState): RoundState {
        if (state.status != GameStatus.IN_PROGRESS) return state
        if (state.currentGuess.length != state.difficulty.wordLength) return state // неполное слово — игнорируем

        val letterStates = WordComparator.compare(
            guess = state.currentGuess,
            target = state.targetWord
        )
        val newAttempt = GuessAttempt(
            word = state.currentGuess,
            letterStates = letterStates
        )
        val newAttempts = state.attempts + newAttempt

        val isWin = state.currentGuess.equals(state.targetWord, ignoreCase = true)
        val isOutOfAttempts = newAttempts.size >= state.difficulty.maxAttempts

        val newStatus = when {
            isWin -> GameStatus.WON
            isOutOfAttempts -> GameStatus.LOST
            else -> GameStatus.IN_PROGRESS
        }

        return state.copy(
            attempts = newAttempts,
            currentGuess = "",
            status = newStatus
        )
    }

    private fun onStartNewRound(targetWord: String, difficulty: Difficulty): RoundState {
        return RoundState(
            targetWord = targetWord.uppercase(),
            difficulty = difficulty
        )
    }
}