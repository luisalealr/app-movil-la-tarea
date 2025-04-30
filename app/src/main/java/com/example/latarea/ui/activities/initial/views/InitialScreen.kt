package com.example.latarea.ui.activities.initial.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.latarea.R
import com.example.latarea.ui.theme.LaTareaTheme

@Preview
@Composable
fun InitialScreen( navigateToLogin:() -> Unit = {} , navigateToSignUp:() -> Unit = {} ){
    LaTareaTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Initial(
                Modifier
                    .align(Alignment.Center)
                    .padding(20.dp),
                navigateToSignUp,
                navigateToLogin
            )
        }
    }
}

@Composable
fun Initial(modifier: Modifier, navigateToSignUp: () -> Unit, navigateToLogin: () -> Unit){
    Box(modifier){
        Column (modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(R.drawable.logo_la_tarea),
                contentDescription = null,
                modifier = Modifier.height(200.dp).align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = "¡¡Organiza tu estudio y tareas para no olvidar nada!!",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.displayMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(30.dp))
            ButtonLogIn(modifier = Modifier.align(Alignment.CenterHorizontally), navigateToLogin)
            Spacer(modifier = Modifier.padding(5.dp))
            ButtonSignUp(modifier = Modifier.align(Alignment.CenterHorizontally), navigateToSignUp)
        }
    }

}

@Composable
fun ButtonLogIn(modifier: Modifier, navigateToLogin: () -> Unit) {
    Box(modifier) {
        Button(
            onClick = { navigateToLogin() },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .width(220.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Text(
                text = "Iniciar Sesión",
                color = Color.Black
                )
        }
    }
}

@Composable
fun ButtonSignUp(modifier: Modifier, navigateToSignUp: () -> Unit) {
    Box(modifier) {
        Button(
            onClick = { navigateToSignUp() },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .width(220.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primaryContainer
            ),
            border = BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primaryContainer
            )
            ){
            Text(
                text = "Iniciar Sesión",
                color = MaterialTheme.colorScheme.primaryContainer
            )
        }
    }
}