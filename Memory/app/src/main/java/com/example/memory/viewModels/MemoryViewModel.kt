package com.example.memory.viewModels

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.memory.R
import com.example.memory.models.MemoryCard
import com.example.memory.models.MemoryCardState
import com.example.memory.models.MemoryDificulty
import com.example.memory.models.MemoryStetings
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class MemoryViewModel: ViewModel() {
    val dificulies = MemoryDificulty.entries.map { it.toString() }.toList()
    var isMenuDropdownExpanded = MutableLiveData(false)
    var setings by mutableStateOf(MemoryStetings())
        private set

    private val memoryCardSet = setOf(
        MemoryCard(id = 0, image = R.drawable.azul_game_img_0),
        MemoryCard(id = 1, image = R.drawable.tori_game_img_1),
        MemoryCard(id = 2, image = R.drawable.pesesito_game_img_2),
        MemoryCard(id = 3, image = R.drawable.piecitos_game_img_3),
        MemoryCard(id = 4, image = R.drawable.neu_game_img_4),
        MemoryCard(id = 5, image = R.drawable.neu_game_img_5),
        MemoryCard(id = 6, image = R.drawable.tsera_game_img_6),
        MemoryCard(id = 7, image = R.drawable.tsera_game_img_7),
        MemoryCard(id = 8, image = R.drawable.tsera_game_img_8),
        MemoryCard(id = 9, image = R.drawable.stegi_game_img_9)
    )

     var memoryCardsList: MutableList<MemoryCard> = memoryCardSet.toMutableList()
        private set

    private var clickedCard = -1
    private var clickedCard2 = -1
    val sleep = MutableLiveData(false)

    fun clickDropdown(isExpanded: Boolean) {
        isMenuDropdownExpanded.value = isExpanded
    }

    fun setDificulty(dificulty: String) {
        MemoryDificulty.entries.forEach { dif ->
            if (dificulty == dif.toString())
                setings = setings.copy(dificulty = dif)
        }
//        Log.i("ACT", setings.dificulty.toString())

        setings = setings.copy(
            numberOfCardPairs = when (setings.dificulty) {
                MemoryDificulty.Facil -> 3
                MemoryDificulty.Media -> 6
                MemoryDificulty.Dificil -> 9
            }
        )
    }

    fun getMemoryCardList() {
        val numCards = setings.numberOfCardPairs

        memoryCardsList = memoryCardSet
            .asSequence()
            .shuffled()
            .take(numCards)
            .toMutableList()


        memoryCardsList = memoryCardsList
            .plus(memoryCardsList)
            .asSequence()
            .shuffled()
            .toMutableList()

        for (i in 0..<numCards*2) {
            memoryCardsList[i] = memoryCardsList[i].copy(id = i)
        }
    }

    fun onClickMemoryCard(cardId: Int) {
//        Log.i("ACT1", memoryCardsList[cardId].memoryCardState.toString())
        setings = setings.copy(isACardClicked = true)
        if (memoryCardsList[cardId].memoryCardState != MemoryCardState.EMPARELLADA) {
//            Log.i("ACT2", memoryCardsList[cardId].memoryCardState.toString())
            memoryCardsList[cardId] = memoryCardsList[cardId].copy(memoryCardState = MemoryCardState.VISIBLE)
//            Log.i("ACT3", memoryCardsList[cardId].memoryCardState.toString())

            if (clickedCard == cardId) {
                memoryCardsList[cardId] = memoryCardsList[cardId].copy(memoryCardState = MemoryCardState.AMAGADA)
                setings = setings.copy(movimientos = setings.movimientos + 1)
                clickedCard = -1
            } else if (clickedCard < 0) {
                clickedCard = cardId
            } else {
                if (memoryCardsList[clickedCard].image == memoryCardsList[cardId].image) {
                    memoryCardsList[clickedCard] = memoryCardsList[clickedCard].copy(memoryCardState = MemoryCardState.EMPARELLADA)
                    memoryCardsList[cardId] = memoryCardsList[cardId].copy(memoryCardState = MemoryCardState.EMPARELLADA)
                    setings = setings.copy(aciertos = setings.aciertos + 1)
                    setings = setings.copy(movimientos = setings.movimientos + 1)
                    clickedCard = -1
                } else {
                    clickedCard2 = cardId
                    sleep.value = true
                    // GO TO SLEEP (el joc s'autura un segon per poder veure la segona carta clicada al memory)
                }
            }
        }

        if (sleep.value == false)
            setings = setings.copy(isACardClicked = false)
    }

    fun afterSleep() {
        memoryCardsList[clickedCard] =
            memoryCardsList[clickedCard].copy(memoryCardState = MemoryCardState.AMAGADA)
        memoryCardsList[clickedCard2] =
            memoryCardsList[clickedCard2].copy(memoryCardState = MemoryCardState.AMAGADA)
        setings = setings.copy(movimientos = setings.movimientos + 1)
        clickedCard = -1

        setings = setings.copy(isACardClicked = false)
        sleep.value = false

    }
}
