package com.example.latarea

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.latarea.ui.theme.LaTareaTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val screenSplash = installSplashScreen();

        super.onCreate(savedInstanceState)

        screenSplash.setKeepOnScreenCondition {false}
        val intent = Intent(this, DetailActivity::class.java)
        startActivity(intent)
        finish()

    }
}

