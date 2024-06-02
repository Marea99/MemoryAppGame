package com.example.memory.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.memory.navigation.Routes

enum class DrawerItems(
    val icon: ImageVector,
    val title: String,
    val route: String
) {
    CLASSIFICATION(Icons.Rounded.List, "Clasidicaciones", Routes.Classificaton.route)
}