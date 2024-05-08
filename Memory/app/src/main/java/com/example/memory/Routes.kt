package com.example.memory

sealed class Routes(val route:String) {
    object Menu: Routes("menu")
    object Game: Routes("game")
    object Results: Routes("results")
}