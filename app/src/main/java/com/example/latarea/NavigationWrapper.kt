package com.example.latarea

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.latarea.ui.activities.tasks.createTask.CreateTask
import com.example.latarea.ui.activities.initial.views.InitialScreen
import com.example.latarea.ui.activities.login.views.LoginScreen
import com.example.latarea.ui.activities.login.views.LoginViewModel
import com.example.latarea.ui.activities.signup.views.SignUpScreen
import com.example.latarea.ui.activities.signup.views.SignUpViewModel
import com.example.latarea.ui.activities.tasks.createNote.CreateNote
import com.example.latarea.ui.activities.tasks.home.TaskAndNotesScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavigationWrapper(navHostController: NavHostController, auth: FirebaseAuth){
    NavHost(navController = navHostController, startDestination = "initial"){
        composable("initial"){
            InitialScreen(
                navigateToLogin = {navHostController.navigate("logIn")},
                navigateToSignUp = {navHostController.navigate("signUp")}
            )
        }
        composable("logIn"){
            val context = LocalContext.current
            LoginScreen(LoginViewModel(context), auth,
                navigateToHome = {
                    navHostController.navigate("home") {
                        popUpTo("logIn") { inclusive = true }
                    }
                })
        }
        composable("signUp"){
            val context = LocalContext.current
            SignUpScreen(
                navigateToHome = {
                    navHostController.navigate("home") {
                        popUpTo("signUp") { inclusive = true }
                    }
                },
                SignUpViewModel(context), auth)
        }
        composable("home") {
            TaskAndNotesScreen(
                createTask = { navHostController.navigate("createTask") },
                createNote = {navHostController.navigate(("createNote"))}
            )
        }

        composable("createTask") {
            CreateTask(navHostController)
        }

        composable("createNote") {
            CreateNote(navHostController)
        }

    }

}