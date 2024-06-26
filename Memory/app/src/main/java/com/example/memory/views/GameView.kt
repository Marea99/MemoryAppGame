package com.example.memory.views

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.memory.navigation.Routes
import com.example.memory.components.MemoryCardComponent
import com.example.memory.components.SoundEffects
import com.example.memory.viewModels.MemoryViewModel
import com.example.memory.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameView(navController: NavController, viewModel: MemoryViewModel) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Memory", color = MaterialTheme.colorScheme.onPrimary) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = Icons.Default.Menu.name,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                }
            )
        }
    ) {
        GameBodyView(paddingValues = it, viewModel, navController)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GameBodyView(paddingValues: PaddingValues, viewModel: MemoryViewModel, navController: NavController) {
    val sleep: Boolean by viewModel.sleep.observeAsState(false)
    val gameOver = viewModel.setings.numberOfCardPairs == viewModel.setings.aciertos
    val soundEffects = SoundEffects()
    val playTurnCardSound by viewModel.playTurnCardsSound.observeAsState(false)
    val playWrongMachSound by viewModel.playWrongMachSound.observeAsState(false)
    val playEndGameSound by viewModel.playEndGameSound.observeAsState(false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FlowRow(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(ScrollState(0)),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center
        ) {
            viewModel.memoryCardsList.forEach {card ->
                MemoryCardComponent(
                    card,
                    dificulty = viewModel.setings.dificulty
                ) {
                    Log.i("CLICK", viewModel.setings.isACardClicked.toString())
                    if (!viewModel.setings.isACardClicked) {
                        viewModel.onClickMemoryCard(card.id)
                        viewModel.setPlayTurnCardsSound(true)
                    }
                }
            }
        }
    }

    if (playTurnCardSound) {
        viewModel.setPlayTurnCardsSound(false)
        soundEffects.PlaySoundEffect(sound = R.raw.turn_card)
    }
    else if (playWrongMachSound) {
        viewModel.setPlayWrongMachSound(false)
        soundEffects.PlaySoundEffect(sound = R.raw.wrong_mach)
    }
    else if (playEndGameSound) {
        viewModel.setPlayEndGameSound(false)
        soundEffects.PlaySoundEffect(sound = R.raw.end_game)
    } else {
        soundEffects.EndSoundEffect()
    }

    LaunchedEffect(sleep) {
        Log.i("SLEEP", sleep.toString())

        if (sleep) {
            viewModel.setPlayWrongMachSound(true)
            delay(1000)
            viewModel.afterSleep()
        }
    }

    LaunchedEffect(gameOver) {
        Log.i("GAME OVER", gameOver.toString())
        if (gameOver) {
            viewModel.finalPartida()
            viewModel.setPlayEndGameSound(true)

            delay(1000)
            navController.popBackStack()
            navController.navigate(Routes.Results.route)
        }
    }
}