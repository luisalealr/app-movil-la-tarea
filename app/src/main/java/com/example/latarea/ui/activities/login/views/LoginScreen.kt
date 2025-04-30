package com.example.latarea.ui.activities.login.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.latarea.R
import com.example.latarea.ui.theme.LaTareaTheme
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(viewModel: LoginViewModel, auth: FirebaseAuth) {
    LaTareaTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Login(
                Modifier
                    .align(Alignment.Center)
                    .padding(20.dp),
                viewModel,
                auth
            )
        }
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel, auth: FirebaseAuth) {
    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)
    Box(modifier) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "¡Hola!",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.displayLarge,
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = "Inicia sesión en tu cuenta",
                style = MaterialTheme.typography.displayMedium
            )
            Spacer(modifier = Modifier.padding(16.dp))
            CustomTextField(
                value = email,
                onValueChange = { viewModel.onLoginChange(it, password) },
                placeholderText = "Ingresa tu correo electrónico",
                keyboardType = KeyboardType.Email
            )
            Spacer(modifier = Modifier.padding(16.dp))
            // Campo de Password
            CustomTextField(
                value = password,
                onValueChange = { viewModel.onLoginChange(email, it) },
                placeholderText = "Ingresa tu contraseña",
                keyboardType = KeyboardType.Password,
                isPassword = true
            )
            Spacer(modifier = Modifier.padding(16.dp))
            BotonIniciarSesion(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                loginEnable,
                auth,
                email,
                password
            ) { viewModel.onLoginSelected() }
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = "o sino",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.padding(16.dp))
            BotonIngresarConGoogle(modifier = Modifier.align(Alignment.CenterHorizontally))

        }
    }
}


@Composable
fun BotonIngresarConGoogle(modifier: Modifier) {
    Box(modifier) {
        Button(
            onClick = {},
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Row {
                Image(
                    painter = painterResource(R.drawable.logo_google),
                    contentDescription = null,
                    modifier = Modifier.height(20.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = "Ingresa con una cuenta de Google"
                )

            }
        }
    }
}

@Composable
fun BotonIniciarSesion(
    modifier: Modifier,
    loginEnable: Boolean,
    auth: FirebaseAuth,
    email: String,
    password: String,
    onLoginSelected: () -> Unit
) {
    Box(modifier) {
        Button(
            onClick = { auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        //navegar
                        Log.i("aris", "LOGIN OK")
                    }else{
                        //Error
                        Log.i("aris", "LOGIN KO")
                    }
            } },
            shape = RoundedCornerShape(5.dp),
            enabled = loginEnable
        ) {
            Text("Iniciar Sesión")
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    val visualTransformation =
        if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(placeholderText) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        maxLines = 1,
        visualTransformation = visualTransformation,
        shape = RoundedCornerShape(5.dp),
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = Color.LightGray,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}


