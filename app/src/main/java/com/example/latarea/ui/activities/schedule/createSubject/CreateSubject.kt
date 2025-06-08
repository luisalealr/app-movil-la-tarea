package com.example.latarea.ui.activities.schedule.createSubject

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.latarea.ui.activities.tasks.home.HomePage

@Composable
fun CreateSubject(navHostController: NavHostController) {
    HomePage(navController = navHostController) {
        SubjectForm()
    }
}

@Composable
fun SubjectForm(){

}