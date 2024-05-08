package com.example.memory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.memory.ui.theme.MemoryTheme
import com.example.memory.viewModels.MemoryViewModel
import com.example.memory.views.GameView
import com.example.memory.views.MenuView
import com.example.memory.views.ResultView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MemoryViewModel by viewModels()
        setContent {
            MemoryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Routes.Menu.route
                    ) {
                        composable(Routes.Menu.route) {
                            MenuView(navController, viewModel)
                        }
                        composable(Routes.Game.route) {
                            GameView(navController, viewModel)
                        }
                        composable(Routes.Results.route) {
                            ResultView(navController, viewModel)
                        }
                    }
                }
            }
        }
    }
}