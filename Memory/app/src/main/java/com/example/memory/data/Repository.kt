package com.example.memory.data

import com.example.memory.data.gamePoints.GamePoints
import com.example.memory.data.gamePoints.GamePointsApplication

class Repository {
    private val daoInterface = GamePointsApplication.database.gamePointsDao()

    // Database functions
    suspend fun saveGamePointsRegister(gamePoints: GamePoints) =
        daoInterface.addGamePointsRegister(gamePoints)

    suspend fun deleteGamePintsRegister(gamePoints: GamePoints) =
        daoInterface.deleteGamePointsRegister(gamePoints)

    suspend fun getEasyGamePointsRegisters() =
        daoInterface.getEasyGamePointsRegisters()

    suspend fun getMediumGamePointsRegisters() =
        daoInterface.getMediumGamePointsRegisters()

    suspend fun getHardGamePintsRegisters() =
        daoInterface.getHardGamePointsRegisters()

    suspend fun getAllGamePointsRegisters() =
        daoInterface.getAllGamePointsRegisters()
}