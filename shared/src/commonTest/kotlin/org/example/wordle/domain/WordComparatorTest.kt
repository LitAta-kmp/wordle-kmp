package org.example.wordle.domain

import kotlin.test.Test
import kotlin.test.assertEquals

class WordComparatorTest {

    @Test
    fun all_letters_correct_when_guess_equals_target() {
        val result = WordComparator.compare(guess = "APPLE", target = "APPLE")

        assertEquals(
            listOf(
                LetterState.CORRECT,
                LetterState.CORRECT,
                LetterState.CORRECT,
                LetterState.CORRECT,
                LetterState.CORRECT
            ),
            result
        )
    }

    @Test
    fun all_letters_absent_when_no_letters_match() {
        val result = WordComparator.compare(guess = "TRUCK", target = "PLANE")

        assertEquals(
            listOf(
                LetterState.ABSENT,
                LetterState.ABSENT,
                LetterState.ABSENT,
                LetterState.ABSENT,
                LetterState.ABSENT
            ),
            result
        )
    }

    @Test
    fun repeated_letter_in_guess_handled_correctly_when_target_has_one_instance() {
        // target SPEED has one P; guess PAPER has two P's — only one should be marked
        val result = WordComparator.compare(guess = "PAPER", target = "APPLE")

        assertEquals(
            listOf(
                LetterState.PRESENT, // P — есть в APPLE, но не на этой позиции
                LetterState.PRESENT, // A — есть, но не на этой позиции
                LetterState.CORRECT, // P — совпадает по позиции
                LetterState.PRESENT, // E — есть, но не на этой позиции
                LetterState.ABSENT   // R — отсутствует
            ),
            result
        )
    }

    @Test
    fun repeated_letter_in_target_matches_up_to_available_count() {
        val result = WordComparator.compare(guess = "ROBOT", target = "ERROR")

        assertEquals(
            listOf(
                LetterState.PRESENT, // R — есть в ERROR, но не на этой позиции
                LetterState.ABSENT,  // O — единственная O в ERROR уже занята позицией 3
                LetterState.ABSENT,  // B — отсутствует
                LetterState.CORRECT, // O — совпадает по позиции (обе O стоят на индексе 3)
                LetterState.ABSENT   // T — отсутствует
            ),
            result
        )
    }

    @Test
    fun lowercase_input_is_handled_case_insensitively() {
        val result = WordComparator.compare(guess = "apple", target = "APPLE")

        assertEquals(
            listOf(
                LetterState.CORRECT,
                LetterState.CORRECT,
                LetterState.CORRECT,
                LetterState.CORRECT,
                LetterState.CORRECT
            ),
            result
        )
    }
}