package com.example.memory.data.gamePoints

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GamePoints::class], version = 1)
abstract class GamePointsDatabase: RoomDatabase() {
    abstract fun gamePointsDao(): GamePointsDao
}