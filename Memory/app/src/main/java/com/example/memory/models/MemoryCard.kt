package com.example.memory.models

import com.example.memory.R

data class MemoryCard (
    var id: Int = -1,
    val image: Int = R.drawable.memory_main_img,
    val backImage: Int = R.drawable.memory_back_img,
    var memoryCardState: MemoryCardState = MemoryCardState.AMAGADA
)
