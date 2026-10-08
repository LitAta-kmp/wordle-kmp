package org.example.wordle.domain

enum class LetterState {
    CORRECT,   // буква верная и на своём месте (зелёный)
    PRESENT,   // буква есть в слове, но не на этом месте (жёлтый)
    ABSENT     // такой буквы в слове нет (серый)
}