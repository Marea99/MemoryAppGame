package com.example.memory.viewModels

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memory.data.Repository
import com.example.memory.data.gamePoints.GamePoints
import com.example.memory.models.MemoryDificulty
import kotlinx.coroutines.launch

class ClassificationViewModel: ViewModel() {
    private val repository = Repository()

    val difficulties = MemoryDificulty.entries
        .map { it.name }
        .plus("All")
        .toList()

    private val _registers = MutableLiveData<List<GamePoints>>()
    val registers = _registers

    private val _showDifClassification = MutableLiveData("All")
    val showDifClassification = _showDifClassification

    fun getRegisters(difficulty: String) {
        Log.i("GET_REGISTERS", difficulty)
        viewModelScope.launch {
            when (difficulty) {
                difficulties[0] ->
                    _registers.value = repository.getEasyGamePointsRegisters()
                difficulties[1] ->
                    _registers.value = repository.getMediumGamePointsRegisters()
                difficulties[2] ->
                    _registers.value = repository.getHardGamePintsRegisters()
                difficulties[3] ->
                    _registers.value = repository.getAllGamePointsRegisters()
            }
        }
    }

    fun deleteAllRegisters() {
        viewModelScope.launch {
            repository.deleteAllGamePointsRegisters()
        }
    }

    fun deleteRegisterById(id: Int) {
        viewModelScope.launch {
            repository.deleteGamePintsRegisterById(id)
        }

        getRegisters(_showDifClassification.value!!)
    }

    fun setShowDiffClassification(difficulty: String) {
        _showDifClassification.value = difficulty
    }
}