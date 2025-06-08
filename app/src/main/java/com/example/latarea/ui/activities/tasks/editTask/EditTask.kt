package com.example.latarea.ui.activities.tasks.editTask

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.latarea.ui.activities.tasks.home.HomePage
import com.example.latarea.ui.theme.LaTareaTheme
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.latarea.data.helper.TokenManager
import com.example.latarea.data.network.RetrofitClient
import com.example.latarea.ui.activities.schedule.createSubject.model.SubjectViewModel
import com.example.latarea.ui.activities.schedule.createSubject.model.SubjectViewModelFactory
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.latarea.ui.activities.tasks.createTask.ColorOption
import com.example.latarea.ui.activities.tasks.createTask.DateInput
import com.example.latarea.ui.activities.tasks.createTask.SubjectOption
import com.example.latarea.ui.activities.tasks.model.TaskViewModel
import com.example.latarea.ui.activities.tasks.model.TaskViewModelFactory
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

@Composable
fun EditTask(taskId: Int, navController: NavController) {
    val taskApi = RetrofitClient.taskApi
    val factoryTask = TaskViewModelFactory(taskApi)
    val taskViewModel: TaskViewModel = viewModel(factory = factoryTask)

    val subjectApi = RetrofitClient.subjectApi
    val factorySubject = SubjectViewModelFactory(subjectApi)
    val subjectViewModel: SubjectViewModel = viewModel(factory = factorySubject)

    val context = LocalContext.current

    var token by remember { mutableStateOf<String?>(null) }

    // Estado para inicializar los campos solo una vez
    var initialized by remember { mutableStateOf(false) }

    // Estados para los campos (inicializados luego de cargar la tarea)
    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf<String?>(null) }
    var selectedSubject by remember { mutableStateOf<Int?>(null) }
    var selectedDate by remember { mutableStateOf<String?>(null) }

    val task = taskViewModel.selectedTask.value
    val subjects = subjectViewModel.subjects.value

    LaunchedEffect(Unit) {
        token = TokenManager.getToken(context)
        token?.let {
            taskViewModel.getTaskById(taskId, it)
            subjectViewModel.loadSubjects(it)
        }
    }

    // Cuando la tarea se carga, inicializamos los estados una sola vez
    LaunchedEffect(task) {
        if (task != null && !initialized) {
            taskTitle = task.title
            taskDescription = task.description ?: ""
            selectedColor = task.colorHexa?.let { "#$it" }
            selectedSubject = task.subjectId
            selectedDate = task.expiresAt?.let {
                // Si viene en formato ISO yyyy-MM-dd, conviertes a d/M/yyyy para mostrar en DateInput
                val parsedDate = try {
                    val localDate = LocalDate.parse(it)
                    localDate.format(DateTimeFormatter.ofPattern("d/M/yyyy"))
                } catch (e: Exception) {
                    it
                }
                parsedDate
            }
            initialized = true
        }
    }

    LaTareaTheme {
        HomePage(navController) {
            if (task != null && subjects.isNotEmpty()) {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    // Aquí usas los mismos campos que en CreateTask pero con los estados precargados
                    BasicTextField(
                        value = taskTitle,
                        onValueChange = { taskTitle = it },
                        textStyle = TextStyle(fontSize = 20.sp, color = Color.DarkGray),
                        modifier = Modifier.fillMaxWidth(),
                        decorationBox = { innerTextField ->
                            if (taskTitle.isEmpty()) {
                                Text(
                                    text = "Título de la tarea",
                                    style = TextStyle(fontSize = 20.sp, color = Color.Gray)
                                )
                            }
                            innerTextField()
                        },
                    )

                    OutlinedTextField(
                        value = taskDescription,
                        onValueChange = { taskDescription = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        placeholder = { Text("Agrega una descripción") },
                        maxLines = 5,
                        singleLine = false,
                    )

                    DateInput (
                        selectedDate = selectedDate ?: "",
                        onDateSelected = { selectedDate = it }
                    )

                    Text(
                        text = "Asignar un color",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.DarkGray
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val colorOptions = listOf(
                            "#BDD299" to "Verde",
                            "#BAA8D6" to "Morado",
                            "#EEBAB7" to "Rojo",
                            "#EEE1B7" to "Amarillo",
                            "#E6B58A" to "Naranja",
                            "#D9E3F8" to "Azul"
                        )
                        colorOptions.forEach { (hex, _) ->
                            ColorOption(
                                color = Color(hex.toColorInt()),
                                isSelected = selectedColor == hex,
                                onSelect = { selectedColor = hex }
                            )
                        }
                    }

                    Text(
                        text = "Asignar una materia",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.DarkGray
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
                            items(subjects) { subject ->
                                SubjectOption(
                                    subject = subject.title,
                                    isSelected = selectedSubject == subject.id,
                                    onSelect = { selectedSubject = subject.id }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {
                            try {
                                val formatterInput = DateTimeFormatter.ofPattern("d/M/yyyy")
                                val formatterOutput = DateTimeFormatter.ISO_LOCAL_DATE

                                val localDate = LocalDate.parse(selectedDate, formatterInput)
                                val formattedDate = localDate.format(formatterOutput)

                                val displayColor = selectedColor?.removePrefix("#")

                                token?.let { tokenValue ->
                                    taskViewModel.updateTask(
                                        tokenValue,
                                        taskId,
                                        taskTitle,
                                        taskDescription,
                                        formattedDate,
                                        displayColor,
                                        selectedSubject
                                    )
                                }
                                navController.navigate("tasks")
                            } catch (e: Exception) {
                                Log.e("EditTask", "Error al parsear la fecha: $selectedDate", e)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(text = "Actualizar", fontSize = 18.sp)
                    }
                }
            } else {
                // Loading
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
