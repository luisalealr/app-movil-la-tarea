package com.example.latarea

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.latarea.ui.theme.LaTareaTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        enableEdgeToEdge()
        setContent {
            navHostController = rememberNavController()
            LaTareaTheme {
                NavigationWrapper(navHostController, auth)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser:FirebaseUser? = auth.currentUser
        if(currentUser != null){
            //navegar al home
            Log.i("aris", "Estoy Logado")
        }
    }
}

