package org.example.wordle.domain

import kotlin.test.Test
import kotlin.test.assertEquals

class GameReducerTest {

    private val initialState = RoundState(
        targetWord = "APPLE",
        difficulty = Difficulty.MEDIUM // wordLength = 5, maxAttempts = 6
    )

    @Test
    fun letter_typed_appends_uppercased_letter_to_current_guess() {
        val result = GameReducer.reduce(initialState, GameIntent.LetterTyped('a'))

        assertEquals("A", result.currentGuess)
    }

    @Test
    fun letter_typed_is_ignored_when_current_guess_already_at_word_length() {
        val state = initialState.copy(currentGuess = "APPLE") // уже 5 букв

        val result = GameReducer.reduce(state, GameIntent.LetterTyped('X'))

        assertEquals("APPLE", result.currentGuess)
    }

    @Test
    fun letter_typed_is_ignored_when_game_is_already_over() {
        val state = initialState.copy(currentGuess = "AP", status = GameStatus.WON)

        val result = GameReducer.reduce(state, GameIntent.LetterTyped('X'))

        assertEquals("AP", result.currentGuess)
    }

    @Test
    fun backspace_removes_last_letter() {
        val state = initialState.copy(currentGuess = "APP")

        val result = GameReducer.reduce(state, GameIntent.BackspacePressed)

        assertEquals("AP", result.currentGuess)
    }

    @Test
    fun backspace_is_ignored_when_current_guess_is_empty() {
        val result = GameReducer.reduce(initialState, GameIntent.BackspacePressed)

        assertEquals("", result.currentGuess)
    }

    @Test
    fun submit_is_ignored_when_current_guess_is_shorter_than_word_length() {
        val state = initialState.copy(currentGuess = "AP") // 2 буквы вместо 5

        val result = GameReducer.reduce(state, GameIntent.SubmitGuess)

        assertEquals("AP", result.currentGuess) // не сброшено
        assertEquals(0, result.attempts.size)   // попытка не добавлена
        assertEquals(GameStatus.IN_PROGRESS, result.status)
    }

    @Test
    fun submit_correct_guess_sets_status_to_won() {
        val state = initialState.copy(currentGuess = "APPLE")

        val result = GameReducer.reduce(state, GameIntent.SubmitGuess)

        assertEquals(GameStatus.WON, result.status)
        assertEquals(1, result.attempts.size)
        assertEquals("", result.currentGuess) // поле ввода очищено для след. попытки
    }

    @Test
    fun submit_wrong_guess_with_attempts_remaining_keeps_status_in_progress() {
        val state = initialState.copy(currentGuess = "ROBOT") // не APPLE, 5 букв

        val result = GameReducer.reduce(state, GameIntent.SubmitGuess)

        assertEquals(GameStatus.IN_PROGRESS, result.status)
        assertEquals(1, result.attempts.size)
        assertEquals(5, result.attemptsLeft) // maxAttempts(6) - attempts.size(1)
    }

    @Test
    fun submit_wrong_guess_on_last_allowed_attempt_sets_status_to_lost() {
        // имитируем, что 5 попыток из 6 уже сделаны (содержимое попыток тут не важно для этого теста)
        val dummyAttempt = GuessAttempt(
            word = "ROBOT",
            letterStates = List(5) { LetterState.ABSENT }
        )
        val state = initialState.copy(
            attempts = List(5) { dummyAttempt },
            currentGuess = "ROBOT" // тоже неверная попытка, шестая по счёту
        )

        val result = GameReducer.reduce(state, GameIntent.SubmitGuess)

        assertEquals(6, result.attempts.size)
        assertEquals(GameStatus.LOST, result.status)
    }

    @Test
    fun start_new_round_resets_attempts_and_status_and_uppercases_target() {
        val finishedState = initialState.copy(
            attempts = listOf(
                GuessAttempt("ROBOT", List(5) { LetterState.ABSENT })
            ),
            currentGuess = "SOME",
            status = GameStatus.WON
        )

        val result = GameReducer.reduce(
            finishedState,
            GameIntent.StartNewRound(targetWord = "crane", difficulty = Difficulty.HARD)
        )

        assertEquals("CRANE", result.targetWord)
        assertEquals(Difficulty.HARD, result.difficulty)
        assertEquals(emptyList(), result.attempts)
        assertEquals("", result.currentGuess)
        assertEquals(GameStatus.IN_PROGRESS, result.status)
    }
}