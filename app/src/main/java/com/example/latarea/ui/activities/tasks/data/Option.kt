package com.example.latarea.ui.activities.tasks.data

import com.example.latarea.R

data class Option(
    val imageResourceId: Int,
    val description: String,
    val route: String
)

val options = listOf(
    Option(R.drawable.logo_tareas, "Tareas", "tasks"),
    Option(R.drawable.logo_horario, "Horario", "schedule"),
    Option(R.drawable.logo_calendario, "Calendario", "calendar"),
    Option(R.drawable.logo_biblioteca, "Mi biblioteca", "myPage"),
)