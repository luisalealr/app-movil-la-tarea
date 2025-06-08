package com.example.latarea.ui.activities.calendar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.latarea.ui.activities.tasks.home.HomePage

@Composable
fun CalendarScreen(navHostController: NavHostController) {
    HomePage(navController = navHostController) {
        CalendarTrack()
    }
}

@Composable
fun CalendarTrack() {
}