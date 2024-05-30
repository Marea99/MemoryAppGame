package com.example.memory.data.gamePoints

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GamePoints::class], version = 2, exportSchema = false)
abstract class GamePointsDatabase: RoomDatabase() {
    abstract fun gamePointsDao(): GamePointsDao
}