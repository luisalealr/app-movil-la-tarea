package com.example.latarea.ui.activities.schedule

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.latarea.ui.activities.schedule.createSubject.SubjectForm
import com.example.latarea.ui.activities.tasks.home.HomePage

@Composable
fun ScheduleScreen(navHostController: NavHostController) {
    HomePage(navController = navHostController) {
        SubjectForm()
    }
}