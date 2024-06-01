package com.example.memory.models


data class MemoryStetings(
    val dificulty: MemoryDificulty = MemoryDificulty.Media,
    val isACardClicked: Boolean = false,
    val numberOfCardPairs: Int = 6,
    val movimientos: Int = 0,
    val points: Int = 0,
    val aciertos: Int = 0,
    val fallos: Int = 0,
    val timeStart: Long = 0L,
    val timeEnd: Long = 0L
)
