package com.example.latarea.ui.activities.tasks.createNote

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.latarea.data.helper.TokenManager
import com.example.latarea.data.network.RetrofitClient
import com.example.latarea.ui.activities.tasks.home.HomePage
import com.example.latarea.ui.activities.tasks.model.NotesViewModel
import com.example.latarea.ui.activities.tasks.model.NotesViewModelFactory
import com.example.latarea.ui.theme.LaTareaTheme

@Composable
fun CreateNote(navController: NavController) {

    val noteApi = RetrofitClient.noteApi
    val factoryNote = NotesViewModelFactory(noteApi)
    val notesViewModel: NotesViewModel = viewModel(factory = factoryNote)

    HomePage {
        NoteForm(notesViewModel, navController)
    }
}

@Composable
fun NoteForm(notesViewModel: NotesViewModel, navController: NavController) {

    val context = LocalContext.current

    var token by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        token = TokenManager.getToken(context)
        println("Token obtenido: $token")
    }

    LaTareaTheme {
        var noteTitle by remember { mutableStateOf("") }
        var noteDescription by remember { mutableStateOf("") }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BasicTextField(
                value = noteTitle,
                onValueChange = { noteTitle = it },
                textStyle = TextStyle(fontSize = 20.sp, color = Color.DarkGray),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                decorationBox = { innerTextField ->
                    if (noteTitle.isEmpty()) {
                        Text(
                            text = "Título de la nota",
                            style = TextStyle(fontSize = 20.sp, color = Color.Gray)
                        )
                    }
                    innerTextField()
                }
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 1.dp),
                thickness = 1.dp,
                color = Color.Gray
            )

            OutlinedTextField(
                value = noteDescription,
                onValueChange = { noteDescription = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                placeholder = { Text("Agrega una descripción") },
                maxLines = 5,
                singleLine = false,
            )

            Spacer(modifier = Modifier.weight(1f))

            CrearNota(notesViewModel, token, noteTitle, noteDescription, navController)
        }
    }
}

@Composable
fun CrearNota(
    viewModel: NotesViewModel,
    token: String?,
    taskTitle: String,
    taskDescription: String,
    navController: NavController
) {

    Button(
        onClick = {
            try {
                token?.let { tokenValue ->
                    viewModel.createNote(
                        tokenValue,
                        taskTitle,
                        taskDescription,
                    )
                }
                navController.popBackStack()
            } catch (e: Exception) {
                Log.e("CrearTarea", "Error al crear la tarea", e)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text = "Guardar", fontSize = 18.sp)
    }
}
