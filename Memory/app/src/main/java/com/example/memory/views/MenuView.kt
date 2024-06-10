package com.example.memory.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.memory.R
import com.example.memory.navigation.Routes
import com.example.memory.components.HelpDialog
import com.example.memory.components.MyOwnRadioButtons
import com.example.memory.components.SoundEffects
import com.example.memory.models.DrawerItems
import com.example.memory.viewModels.MemoryViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuView(navController: NavController, viewModel: MemoryViewModel) {
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
                            scope.launch { drawerState.close() }
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
            MenuBodyView(it, viewModel, navController)
        }
    }
}



@Composable
fun MenuBodyView(paddingValues: PaddingValues, viewModel: MemoryViewModel, navController: NavController) {
    val showingHelpDialog by viewModel.showingHelpDialog.observeAsState(false)
    val menuStarted: Boolean by viewModel.sleep.observeAsState(false)
    val soundEffects = SoundEffects()
    val playSoundEffect: Boolean by viewModel.playSoundEffect1.observeAsState(false)
    val endSoundEffect: Boolean by viewModel.endSoundEffect.observeAsState(false)

    val rainbowColorsBrush = remember {
        Brush.sweepGradient(
            listOf(
                Color(0xFF9575CD),
                Color(0xFFBA68C8),
                Color(0xFFE57373),
                Color(0xFFFFB74D),
                Color(0xFFFFF176),
                Color(0xFFAED581),
                Color(0xFF4DD0E1),
                Color(0xFF9575CD)
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(R.drawable.memory_main_img),
            contentDescription = "Memory Logo, un pescado",
            modifier = Modifier
                .fillMaxHeight(0.3F)
                .clip(RoundedCornerShape(16.dp))
                .border(4.dp, rainbowColorsBrush, RoundedCornerShape(16.dp))
        )

        Log.i("DIF", viewModel.setings.dificulty.toString())
        MyOwnRadioButtons(viewModel.setings.dificulty.name, viewModel.dificulies, viewModel)

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier
                    .weight(1F)
                    .padding(end = 4.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = { viewModel.showHelpDialog() }
            ) {
                Icon(imageVector = Icons.Default.Info, contentDescription = "Help")
            }
            Button(
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 4.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    viewModel.iniciPartida()

                    if (menuStarted)
                        navController.popBackStack()
                    else
                        viewModel.menuStarted.value = false

                    viewModel.startSoundEffect()
                    navController.navigate(Routes.Game.route)
                }
            ) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play")
                if (playSoundEffect) {
                    soundEffects.PlaySoundEffect(sound = R.raw.put_cards)
                    viewModel.stopSoundEffect()
                } else if (endSoundEffect) {
                    soundEffects.EndSoundEffect()
                    viewModel.endSoundEffectToFalse()
                }
            }
        }

        if (showingHelpDialog) {
            Log.i("HELP", showingHelpDialog.toString())
            HelpDialog(viewModel)
        }
    }
}