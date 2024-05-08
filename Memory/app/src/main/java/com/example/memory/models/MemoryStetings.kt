package com.example.memory.models

data class MemoryStetings(
    val dificulty: MemoryDificulty = MemoryDificulty.Media,
    val isACardClicked: Boolean = false,
    val numberOfCardPairs: Int = 6,
    val movimientos: Int = 0,
    val points: Int = 10,
    val aciertos: Int = 0,
    val fallos: Int = 0
)
