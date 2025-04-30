package com.example.latarea

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.latarea.ui.activities.initial.views.InitialScreen
import com.example.latarea.ui.activities.login.views.LoginScreen
import com.example.latarea.ui.activities.login.views.LoginViewModel
import com.example.latarea.ui.activities.signup.views.SignUpScreen
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
            LoginScreen(LoginViewModel(), auth)
        }
        composable("signUp"){
            SignUpScreen(auth)
        }

    }

}