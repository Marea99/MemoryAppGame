package com.example.memory.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.memory.viewModels.MemoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultView(navController: NavController, viewModel: MemoryViewModel) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Memory", color = MaterialTheme.colorScheme.onPrimary) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) {
        Text(text = "Has conseguido ${viewModel.setings.aciertos} aciertos en ${viewModel.setings.movimientos} movimientos", modifier = Modifier.padding(it))
    }
}