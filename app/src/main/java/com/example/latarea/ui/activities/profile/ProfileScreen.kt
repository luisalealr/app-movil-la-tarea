package com.example.latarea.ui.activities.profile

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.latarea.ui.activities.tasks.home.HomePage

@Composable
fun ProfileScreen(navHostController: NavHostController) {
    HomePage(navController = navHostController) {
        ProfileDashboard()
    }
}

@Composable
fun ProfileDashboard(){

}