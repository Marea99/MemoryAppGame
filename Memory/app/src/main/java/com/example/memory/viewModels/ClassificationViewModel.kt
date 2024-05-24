package com.example.memory.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memory.data.Repository
import com.example.memory.data.gamePoints.GamePoints
import kotlinx.coroutines.launch

class ClassificationViewModel: ViewModel() {
    private val repository = Repository()

    private val _registers = MutableLiveData<List<GamePoints>>()
    val registers = _registers

    fun getAllRegisters(){
        viewModelScope.launch {
            _registers.value = repository.getAllGamePointsRegisters()
        }
    }
}