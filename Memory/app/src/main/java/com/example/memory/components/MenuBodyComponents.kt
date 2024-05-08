package com.example.memory.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.PopupProperties
import com.example.memory.models.MemoryCardState
import com.example.memory.viewModels.MemoryViewModel

@Composable
fun MyOwnDropdown(info: List<String>, viewModel: MemoryViewModel) {
    val expanded by viewModel.isMenuDropdownExpanded.observeAsState(false)

    OutlinedTextField(
        value = viewModel.setings.dificulty.toString(),
        onValueChange = { viewModel.setDificulty(it) },
        enabled = false,
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                viewModel.clickDropdown(true)
            }
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
        colors = TextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = RoundedCornerShape(8.dp)
    )



    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { viewModel.clickDropdown(false) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            .background(Color.Cyan, shape = RoundedCornerShape(8.dp)),
        properties = PopupProperties()
    ) {
        info.forEach {
            DropdownMenuItem(
                text = { Text(text = it) },
                onClick = {
                    viewModel.clickDropdown(false)
                    viewModel.setDificulty(it)
                }
            )
        }
    }
}

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
fun Help() {
    Dialog(
        onDismissRequest = { /*TODO*/ }
    ) {

    }
}