package com.example.memory.data.gamePoints

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.memory.models.MemoryDificulty

@Entity("GamePointsEntity")
data class GamePoints(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val points: Int,
    val difficulty: MemoryDificulty,
    /*val nivel: Int = when(difficulty) {
        MemoryDificulty.Facil -> 1
        MemoryDificulty.Media -> 2
        MemoryDificulty.Dificil -> 3
    }*/
)
