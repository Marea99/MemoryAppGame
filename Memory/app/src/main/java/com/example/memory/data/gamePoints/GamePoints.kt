package com.example.memory.data.gamePoints

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.memory.models.MemoryDificulty

@Entity("GamePointsEntity")
data class GamePoints(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "points") val points: Int,
    @ColumnInfo(name = "difficulty") val difficulty: MemoryDificulty,
    @ColumnInfo(name = "nivel") val nivel: Int = when(difficulty) {
        MemoryDificulty.Facil -> 1
        MemoryDificulty.Media -> 2
        MemoryDificulty.Dificil -> 3
    }
)
