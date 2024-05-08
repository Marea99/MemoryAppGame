package com.example.memory.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.memory.models.MemoryCard
import com.example.memory.models.MemoryCardState
import com.example.memory.models.MemoryDificulty

@Composable
fun MemoryCardComponent(
    memoryCard: MemoryCard,
    dificulty: MemoryDificulty,
    onClick: () -> Unit
) {
    when(memoryCard.memoryCardState) {
        MemoryCardState.AMAGADA ->
            Image(
                painter = painterResource(memoryCard.backImage),
                contentDescription = "Hiden card",
                modifier = Modifier
                    .size(getMemoryCardSize(dificulty))
                    .padding(4.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(8.dp, MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(16.dp))
                    .clickable { onClick() },
            )
        MemoryCardState.VISIBLE ->
            Image(
                painter = painterResource(memoryCard.image),
                contentDescription = "Clicked card",
                modifier = Modifier
                    .size(getMemoryCardSize(dificulty))
                    .padding(4.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(8.dp, MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(16.dp))
                    .clickable { onClick() }
            )
        MemoryCardState.EMPARELLADA ->
            Image(
                painter = painterResource(memoryCard.image),
                contentDescription = "Card already paired",
                modifier = Modifier
                    .size(getMemoryCardSize(dificulty))
                    .padding(4.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(8.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(16.dp))
                    .clickable { onClick() }
            )
    }
}

fun getMemoryCardSize(dificulty: MemoryDificulty): Dp {
    // mirar de buscar el tamaño de la pantalla para poder hacer una vista adaptable
    return when (dificulty) {
        MemoryDificulty.Facil -> 164.dp
        MemoryDificulty.Media -> 124.dp
        MemoryDificulty.Dificil -> 116.dp
    }
}