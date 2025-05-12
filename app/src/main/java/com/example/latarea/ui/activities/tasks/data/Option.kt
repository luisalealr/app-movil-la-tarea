package com.example.latarea.ui.activities.tasks.data

import com.example.latarea.R

data class Option(
    val imageResourceId: Int,
    val description: String,
)

val options = listOf(
    Option(R.drawable.logo_tareas, "Tareas"),
    Option(R.drawable.logo_horario, "Horario"),
    Option(R.drawable.logo_calendario, "Calendario"),
    Option(R.drawable.logo_biblioteca, "Mi biblioteca"),
)