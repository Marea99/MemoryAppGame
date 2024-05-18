package com.example.memory.data.gamePoints

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.memory.models.MemoryDificulty

@Dao
interface GamePointsDao  {
    @Query("SELECT * FROM GamePointsEntity WHERE difficulty = :difficulty ORDER BY points desc")
    suspend fun getEasyGamePointsRegisters(difficulty: MemoryDificulty = MemoryDificulty.Facil): MutableList<GamePoints>

    @Query("SELECT * FROM GamePointsEntity WHERE difficulty = :difficulty ORDER BY points desc")
    suspend fun getMediumGamePointsRegisters(difficulty: MemoryDificulty = MemoryDificulty.Media): MutableList<GamePoints>

    @Query("SELECT * FROM GamePointsEntity WHERE difficulty = :difficulty ORDER BY points desc")
    suspend fun getHardGamePointsRegisters(difficulty: MemoryDificulty = MemoryDificulty.Dificil): MutableList<GamePoints>

    @Query("SELECT * FROM GamePointsEntity ORDER BY points desc")
    suspend fun getAllGamePointsRegisters(): MutableList<GamePoints>

    @Insert
    suspend fun addGamePointsRegister(gamePoints: GamePoints)

    @Delete
    suspend fun deleteGamePointsRegister(gamePoints: GamePoints)
}