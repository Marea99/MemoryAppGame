package com.example.memory.components


import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.memory.models.DrawerItems
import com.example.memory.navigation.Routes
import com.example.memory.viewModels.ClassificationViewModel
import com.example.memory.viewModels.MemoryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MyOwnRadioButtons(state: String, info: List<String>, viewModel: MemoryViewModel) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.Center
    ) {
        info.forEach {
            MyOwnRadioButtonItem(it, state, viewModel)
        }
    }
}

@Composable
fun MyOwnRadioButtonItem(text:String, state: String, viewModel: MemoryViewModel) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = text == state,
            onClick = { viewModel.setDificulty(text) }
        )
        Text(text, modifier = Modifier.padding(end = 8.dp))
    }
}

@Composable
fun HelpDialog(viewModel: MemoryViewModel) {
    Dialog(
        onDismissRequest = { viewModel.dismissHelpDialog() }
    ) {
        Card(
            //modifier = Modifier.padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .verticalScroll(ScrollState(0)),
                horizontalAlignment = AbsoluteAlignment.Left,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Instrucciones",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    text = "Este es un juego de memoria que consiste en encontar las parejas escondidas. " +
                            "En este juego hay varias dificultades y en cada una de ellas se tendra que encontrar " +
                            "un numero determinado de parejas. A continuación se explican los diferentes niveles:\n" +
                            "· Facil: se han de encontrar 3 parejas.\n· Normal: Se han de encontrar 6 parejas.\n" +
                            "· Dificil: se han de encontrar 9 parejas."
                )
            }
        }
    }
}
