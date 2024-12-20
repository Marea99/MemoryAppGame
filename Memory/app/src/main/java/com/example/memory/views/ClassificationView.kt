package com.example.memory.views

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.memory.data.gamePoints.GamePoints
import com.example.memory.viewModels.ClassificationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassificationView(navController: NavController, viewModel: ClassificationViewModel) {
    val showDifClassification by viewModel.showDifClassification.observeAsState("All")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Clasificaciónes") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = Icons.Default.ArrowBack.name,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                }
            )
        },
        bottomBar = {
            viewModel.getRegisters(showDifClassification)
            BottomNavigationBar(viewModel,showDifClassification)
        }
    ) {
        ClassificationBodyView(it, viewModel)
    }
}

@Composable
fun BottomNavigationBar(viewModel: ClassificationViewModel, showDifClassification: String) {
    //val showDifClassification by viewModel.showDifClassification.observeAsState("All")
    val difficultiesColor = mapOf(
        viewModel.difficulties[0] to Color(0xFF4CAF50),
        viewModel.difficulties[1] to Color(0xFFFFC107),
        viewModel.difficulties[2] to Color(0xFFF44336),
        viewModel.difficulties[3] to MaterialTheme.colorScheme.onSurface
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        viewModel.difficulties.forEach { item ->
            NavigationBarItem(
                selected = showDifClassification == item,
                onClick = {
                    viewModel.setShowDiffClassification(item)
                    viewModel.getRegisters(showDifClassification)
                    Log.i("ShowDiff", showDifClassification)
                },
                icon = { Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = "",
                    tint = difficultiesColor[item] ?: MaterialTheme.colorScheme.onSurface
                ) },
                label = { Text(text = item) },
                alwaysShowLabel = true
            )
        }
    }
}

@Composable
fun ClassificationBodyView(paddingValues: PaddingValues, viewModel: ClassificationViewModel) {
    val registers: List<GamePoints> by viewModel.registers.observeAsState(emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(registers) { register ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = register.name,
                        modifier = Modifier
                            .weight(1F)
                            .horizontalScroll(enabled = true, state = ScrollState(0))
                    )

                    Text(
                        text = register.difficulty.toString(),
                        modifier = Modifier.weight(0.5F)
                    )

                    Text(
                        text = register.points.toString(),
                        modifier = Modifier.weight(0.3F)
                    )

                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier
                            .clickable { viewModel.deleteRegisterById(register.id) }
                            .weight(0.2F)
                    )
                }
            }
        }
    }
}

