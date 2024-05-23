package com.example.memory.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.memory.navigation.Routes
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
        ResultBodyView(paddingValues = it, viewModel, navController)
    }
}

@Composable
fun ResultBodyView(paddingValues: PaddingValues, viewModel: MemoryViewModel, navController: NavController) {
    val name by viewModel.name.observeAsState("")
    val savedData by viewModel.savedData.observeAsState(false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "¡FELICIDADES!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.padding(vertical = 4.dp))

            Text(
                text = "Has conseguido solucionar el memori ${viewModel.setings.dificulty} " +
                    "en ${viewModel.setings.movimientos} movimientos y con un todal de " +
                    "${viewModel.setings.points} puntos.",
                fontFamily = FontFamily.Monospace
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            if (savedData) {
                Text(text = "Tu resultado se ha guardado correctamente.")
            } else {
                Text(text = "Si desea guardar su resultado introduzca su nombre y pulse el boton")
            }
            
            Spacer(modifier = Modifier.padding(vertical = 4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = name,
                    onValueChange = {
                        viewModel.setName(it)
                    },
                    enabled = !savedData
                )
                Button(
                    modifier = Modifier,
                    onClick = {
                        viewModel.saveGameResult()
                    },
                    enabled = !savedData
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Save results",
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier
                    .weight(1F)
                    .padding(end = 4.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    viewModel.resetGame()
                    navController.popBackStack()
                    //navController.navigate(Routes.Menu.route)
                }
            ) {
                Icon(imageVector = Icons.Default.Home, contentDescription = "Go to menu")
            }
            Button(
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 4.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    viewModel.resetGame()
                    viewModel.getMemoryCardList()
                    navController.popBackStack()
                    navController.navigate(Routes.Game.route)
                }
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Re-play")
            }
        }
    }
}