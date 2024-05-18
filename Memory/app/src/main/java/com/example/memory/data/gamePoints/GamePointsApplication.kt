package com.example.memory.data.gamePoints

import android.app.Application
import androidx.room.Room

class GamePointsApplication: Application() {
    companion object {
        lateinit var database: GamePointsDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            GamePointsDatabase::class.java,
            "GamePointsDatabase"
        ).build()
    }
}