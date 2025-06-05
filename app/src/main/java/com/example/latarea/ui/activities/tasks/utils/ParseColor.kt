package com.example.latarea.ui.activities.tasks.utils

import androidx.compose.ui.graphics.Color

fun ParseColor(colorString: String): Color {
    // Agrega FF para el alpha si no tiene (si solo vienen 6 caracteres)
    val colorInt = if (colorString.length == 6) {
        // Parsear y agregar alpha FF delante
        android.graphics.Color.parseColor("#FF$colorString")
    } else {
        // Parsear directamente si ya tiene alpha
        android.graphics.Color.parseColor("#$colorString")
    }

    return Color(colorInt)
}