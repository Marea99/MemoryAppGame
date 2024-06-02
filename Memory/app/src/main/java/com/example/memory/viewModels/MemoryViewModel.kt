package com.example.memory.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memory.R
import com.example.memory.data.Repository
import com.example.memory.data.gamePoints.GamePoints
import com.example.memory.models.MemoryCard
import com.example.memory.models.MemoryCardState
import com.example.memory.models.MemoryDificulty
import com.example.memory.models.MemoryStetings
import kotlinx.coroutines.launch

class MemoryViewModel: ViewModel() {
    private val repository = Repository()

    val dificulies = MemoryDificulty.entries.map { it.name }.toList()
    var menuStarted = MutableLiveData(false) // Per saver si ja has estat al menu al menys 1 cop abans
    var showingHelpDialog = MutableLiveData(false)
        private set

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

    private val _name = MutableLiveData("")
    val name = _name

    private val _savedData = MutableLiveData(false)
    val savedData = _savedData

    fun showHelpDialog() {
        showingHelpDialog.value = true
    }

    fun dismissHelpDialog() {
        showingHelpDialog.value = false
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



    fun iniciPartida() {
        setings = setings.copy(timeStart = System.currentTimeMillis())
        getMemoryCardList()
    }

    private fun getMemoryCardList() {
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
                if (setings.dificulty == MemoryDificulty.Dificil)
                    setings = setings.copy(fallos = setings.fallos + 1)
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

        setings = setings.copy(fallos = setings.fallos + 1)
        setings = setings.copy(movimientos = setings.movimientos + 1)
        clickedCard = -1

        setings = setings.copy(isACardClicked = false)
        sleep.value = false

    }

    fun finalPartida() {
        setings = setings.copy(timeEnd = System.currentTimeMillis())
        calcularPuntuacion()
    }

    private fun calcularPuntuacion() {
        val temps = (setings.timeEnd - setings.timeStart) / 1000
        Log.i("CALC_POINTS", "temps ${temps}s")

        val plusTiempo: Int = when (setings.dificulty) {
            MemoryDificulty.Facil,
            MemoryDificulty.Media -> {
                if (temps <= 30) 10
                else if (temps <= 60) 5
                else 0
            }
            MemoryDificulty.Dificil -> {
                if (temps <= 50) 10
                else if (temps <= 80) 5
                else 0
            }
        }

        setings = setings.copy(
            points = (setings.aciertos * 2) - setings.fallos + plusTiempo
        )
    }

    fun setName(name: String) {
        _name.value = name
    }

    fun saveGameResult() {
        val gamePoints = GamePoints(
            name = _name.value ?: "Anon",
            points = setings.points,
            difficulty = setings.dificulty
        )

        viewModelScope.launch {
            repository.saveGamePointsRegister(gamePoints)
        }
        //Log.i("SAVE", "${gamePoints.name} has ${gamePoints.points} in the ${gamePoints.difficulty} mode.")

        _savedData.value = true
        _name.value = ""
    }

    fun resetGame() {
        setings = setings.copy(
            movimientos = 0,
            points = 0,
            aciertos = 0,
            fallos = 0,
            timeStart = 0L,
            timeEnd = 0L
        )
        _name.value = ""
        _savedData.value = false
    }
}
