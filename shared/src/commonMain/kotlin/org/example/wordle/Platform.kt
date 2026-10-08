package org.example.wordle

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform