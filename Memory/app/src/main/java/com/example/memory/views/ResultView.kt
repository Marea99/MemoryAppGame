package com.example.memory.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.memory.models.DrawerItems
import com.example.memory.navigation.Routes
import com.example.memory.viewModels.MemoryViewModel
import com.example.memory.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultView(navController: NavController, viewModel: MemoryViewModel) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                DrawerItems.entries.forEach { drawerItem ->
                    NavigationDrawerItem(
                        label = { Text(text = drawerItem.title) },
                        icon = { Icon(imageVector = drawerItem.icon, contentDescription = drawerItem.title) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.open() }
                            navController.navigate(drawerItem.route)
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = "Memory", color = MaterialTheme.colorScheme.onPrimary) },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = Icons.Default.Menu.name,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable {
                                    scope.launch { drawerState.open() }
                                }
                        )
                    }
                )
            }
        ) {
            ResultBodyView(paddingValues = it, viewModel, navController)
        }
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

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = viewModel.setings.points.toString(), style = MaterialTheme.typography.headlineMedium)
                    Text(text = "Puntos", style = MaterialTheme.typography.titleLarge)
                }

                Divider(modifier = Modifier.height(40.dp).width(2.dp),
                    color = MaterialTheme.colorScheme.secondary)

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "${(viewModel.setings.timeEnd-viewModel.setings.timeStart)/1000}s",
                        style = MaterialTheme.typography.headlineMedium)
                    Text(text = "Tiempo", style = MaterialTheme.typography.titleLarge)
                }
            }
            /*
            Text(
                text = "Has conseguido solucionar el memori ${viewModel.setings.dificulty} " +
                    "en ${(viewModel.setings.timeEnd-viewModel.setings.timeStart)/1000}s y " +
                    "con un todal de ${viewModel.setings.points} puntos.",
                fontFamily = FontFamily.Monospace
            )
             */
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
                    enabled = !savedData,
                    singleLine = true,
                    maxLines = 1
                )
                Button(
                    modifier = Modifier,
                    shape = RoundedCornerShape(16.dp), // CutCornerShape
                    onClick = {
                        viewModel.saveGameResult()
                    },
                    enabled = !savedData
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_save_alt_24),
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
                    viewModel.iniciPartida()
                    navController.popBackStack()
                    navController.navigate(Routes.Game.route)
                }
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Re-play")
            }
        }
    }
}