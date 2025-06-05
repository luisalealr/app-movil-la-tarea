package com.example.latarea.ui.activities.tasks.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.latarea.data.helper.TokenManager
import com.example.latarea.data.network.RetrofitClient
import com.example.latarea.ui.activities.tasks.components.NotesCard
import com.example.latarea.ui.activities.tasks.components.TaskCard
import com.example.latarea.ui.activities.tasks.model.NotesViewModel
import com.example.latarea.ui.activities.tasks.model.NotesViewModelFactory
import com.example.latarea.ui.activities.tasks.model.TaskViewModel
import com.example.latarea.ui.activities.tasks.model.TaskViewModelFactory
import com.example.latarea.ui.theme.LaTareaTheme

@Composable
fun TaskAndNotesScreen(createTask: () -> Unit, createNote: () -> Unit) {
    val taskApi = RetrofitClient.taskApi
    val factoryTask = TaskViewModelFactory(taskApi)
    val tasksViewModel: TaskViewModel = viewModel(factory = factoryTask)

    val noteApi = RetrofitClient.noteApi
    val factoryNote = NotesViewModelFactory(noteApi)
    val notesViewModel: NotesViewModel = viewModel(factory = factoryNote)
    HomePage {
        TaskAndNotesPage(tasksViewModel, notesViewModel, createTask, createNote)
    }
}

@Composable
fun TaskAndNotesPage(
    tasksViewModel: TaskViewModel,
    notesViewModel: NotesViewModel,
    createTask: () -> Unit,
    createNote: () -> Unit
) {
    val context = LocalContext.current
    val tasks = tasksViewModel.tasks.value
    val notes = notesViewModel.notes.value

    // Estado para guardar el token
    var token by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        token = TokenManager.getToken(context)
        println("Token obtenido: $token")
        token?.let {
            tasksViewModel.loadTasks(it)
            notesViewModel.loadNotes(it)
        } ?: println("No se obtuvo token")
    }

    LaTareaTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.height(250.dp)
                ) {
                    Text(
                        text = "Notas",
                        style = MaterialTheme.typography.labelLarge,
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 1.dp,
                        color = Color.Gray
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(0.95f)
                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(notes) { note ->
                                NotesCard(note)
                            }
                        }
                    }
                }
                Column {
                    Text(
                        text = "Tareas",
                        style = MaterialTheme.typography.labelLarge,
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 1.dp,
                        color = Color.Gray
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(0.95f)
                    ) {
                        LazyColumn {
                            items(tasks) { task ->
                                TaskCard(task)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
            AddSomething(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(5.dp),
                createTask,
                createNote
            )
        }
    }
}

@Composable
fun AddSomething(
    modifier: Modifier = Modifier,
    createTask: () -> Unit,
    createNote: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        if (expanded) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 6.dp, bottom = 80.dp)
            ) {
                SmallFabItem(
                    text = "Nota rápida",
                    navegar = createNote,

                )
                SmallFabItem(
                    text = "Tarea",
                    navegar = createTask
                )
            }
        }
        FloatingActionButton(
            onClick = { expanded = !expanded },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 6.dp, bottom = 16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add something",
                tint = Color.White
            )
        }
    }
}

@Composable
fun SmallFabItem(text: String, navegar: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Button(
            onClick = { navegar() }
        ){
            Text(text)
        }
    }
}
