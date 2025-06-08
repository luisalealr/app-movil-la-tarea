package com.example.latarea.ui.activities.signup.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun SignUpScreen(navigateToHome: () -> Unit, viewModel: SignUpViewModel, auth: FirebaseAuth,) {
    LaTareaTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            SignUp(
                Modifier
                    .align(Alignment.Center)
                    .padding(20.dp),
                navigateToHome,
                viewModel,
                auth
            )
        }
    }

}

@Composable
fun SignUp(modifier: Modifier, navigateToHome: () -> Unit, viewModel: SignUpViewModel, auth: FirebaseAuth,) {
    val name: String by viewModel.name.observeAsState(initial = "")
    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val signUpEnable: Boolean by viewModel.registerEnable.observeAsState(initial = false)
    Box(modifier) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "¡Bienvenido!",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.displayLarge,
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = "Regístrate para La Tarea!!",
                style = MaterialTheme.typography.displaySmall
            )
            Spacer(modifier = Modifier.padding(16.dp))
            // Campo de nombre
            CustomTextField(
                value = name,
                onValueChange = { viewModel.onRegisterChange(it, email, password) },
                placeholderText = "Ingresa tu nombre",
                keyboardType = KeyboardType.Text
            )
            Spacer(modifier = Modifier.padding(16.dp))
            CustomTextField(
                value = email,
                onValueChange = { viewModel.onRegisterChange(name, it, password)  },
                placeholderText = "Ingresa tu correo electrónico",
                keyboardType = KeyboardType.Email
            )
            Spacer(modifier = Modifier.padding(16.dp))
            // Campo de Password
            CustomTextField(
                value = password,
                onValueChange = { viewModel.onRegisterChange(name, email, it)  },
                placeholderText = "Ingresa tu contraseña",
                keyboardType = KeyboardType.Password,
                isPassword = true
            )
            Spacer(modifier = Modifier.padding(16.dp))
            BotonRegistro(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                auth,
                name,
                email,
                password,
                viewModel,
                navigateToHome
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = "o sino",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.padding(16.dp))
           // BotonIngresarConGoogle(modifier = Modifier.align(Alignment.CenterHorizontally))

        }
    }
}


@Composable
fun BotonRegistro(
    modifier: Modifier,
    auth: FirebaseAuth,
    name: String,
    email: String,
    password: String,
    viewModel: SignUpViewModel,
    navigateToHome: () -> Unit
)  {
    Box(modifier) {
        Button(
            onClick = {
                viewModel.firebaseRegister(auth, email, password, name) { success ->
                    if (success) {
                        navigateToHome()
                    } else {
                        // Mostrar error si quieres
                    }
                }
            },
            shape = RoundedCornerShape(5.dp)
        ) {
            Text("Registrarse")
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
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Ingresa con una cuenta de Google"
                )

            }
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