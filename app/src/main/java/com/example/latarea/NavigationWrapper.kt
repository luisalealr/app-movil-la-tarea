package com.example.latarea

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.latarea.ui.activities.calendar.CalendarScreen
import com.example.latarea.ui.activities.schedule.createSubject.CreateSubject
import com.example.latarea.ui.activities.tasks.createTask.CreateTask
import com.example.latarea.ui.activities.initial.views.InitialScreen
import com.example.latarea.ui.activities.login.views.LoginScreen
import com.example.latarea.ui.activities.login.views.LoginViewModel
import com.example.latarea.ui.activities.profile.ProfileScreen
import com.example.latarea.ui.activities.schedule.ScheduleScreen
import com.example.latarea.ui.activities.signup.views.SignUpScreen
import com.example.latarea.ui.activities.signup.views.SignUpViewModel
import com.example.latarea.ui.activities.tasks.NoteInfo
import com.example.latarea.ui.activities.tasks.TaskInfo
import com.example.latarea.ui.activities.tasks.createNote.CreateNote
import com.example.latarea.ui.activities.tasks.editTask.EditTask
import com.example.latarea.ui.activities.tasks.home.TaskAndNotesScreen
import com.google.firebase.auth.FirebaseAuth

@RequiresApi(Build.VERSION_CODES.N)
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
                    navHostController.navigate("tasks") {
                        popUpTo("logIn") { inclusive = true }
                    }
                })
        }
        composable("signUp"){
            val context = LocalContext.current
            SignUpScreen(
                navigateToHome = {
                    navHostController.navigate("tasks") {
                        popUpTo("signUp") { inclusive = true }
                    }
                },
                SignUpViewModel(context), auth)
        }
        composable("tasks") {
            TaskAndNotesScreen(
                createTask = { navHostController.navigate("createTask") },
                createNote = { navHostController.navigate("createNote") },
                navHostController
            )
        }

        composable("createTask") {
            CreateTask(navHostController)
        }

        composable("createNote") {
            CreateNote(navHostController)
        }

        composable("schedule") {
            ScheduleScreen(navHostController)
        }

        composable("createSubject") {
            CreateSubject(navHostController)
        }

        composable("myPage") {
            ProfileScreen(navHostController)
        }

        composable("calendar") {
            CalendarScreen(navHostController)
        }

        composable(
            "taskDetail/{taskId}",
            arguments = listOf(navArgument ("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
            TaskInfo(taskId, navHostController)
        }

        composable(
            "taskUpdate/{taskId}",
            arguments = listOf(navArgument ("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
            EditTask(taskId, navHostController)
        }

        composable(
            "noteDetail/{noteId}",
            arguments = listOf(navArgument ("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
            NoteInfo(noteId, navHostController)
        }



    }
}