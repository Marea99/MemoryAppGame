package com.example.memory.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.memory.viewModels.ClassificationViewModel
import com.example.memory.viewModels.MemoryViewModel
import com.example.memory.views.ClassificationView
import com.example.memory.views.GameView
import com.example.memory.views.MenuView
import com.example.memory.views.ResultView

@Composable
fun NavMemory(memoryViewModel: MemoryViewModel, classificationViewModel: ClassificationViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Menu.route
    ) {
        composable(Routes.Menu.route) {
            MenuView(navController, memoryViewModel)
        }
        composable(Routes.Game.route) {
            GameView(navController, memoryViewModel)
        }
        composable(Routes.Results.route) {
            ResultView(navController, memoryViewModel)
        }

        composable(Routes.Classificaton.route) {
            ClassificationView(navController, classificationViewModel)
        }
    }
}