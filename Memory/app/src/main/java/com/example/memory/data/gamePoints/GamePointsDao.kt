package com.example.memory.data.gamePoints

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
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

    @Query("SELECT * FROM GamePointsEntity ORDER BY nivel, points desc")
    suspend fun getAllGamePointsRegisters(): MutableList<GamePoints>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGamePointsRegister(gamePoints: GamePoints)

    @Delete
    suspend fun deleteGamePointsRegister(gamePoints: GamePoints)

    @Query("DELETE FROM GamePointsEntity WHERE id = :id")
    suspend fun deleteGamePointsRegisterById(id: Int)

    @Query("DELETE FROM GamePointsEntity")
    suspend fun deleteAllGamePointsRegisters()
}