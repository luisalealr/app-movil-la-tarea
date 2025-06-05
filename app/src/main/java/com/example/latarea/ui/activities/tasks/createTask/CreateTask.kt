package com.example.latarea.ui.activities.tasks.createTask

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.latarea.data.helper.TokenManager
import com.example.latarea.data.network.RetrofitClient
import com.example.latarea.ui.activities.createSubject.views.SubjectViewModel
import com.example.latarea.ui.activities.createSubject.views.SubjectViewModelFactory
import com.example.latarea.ui.activities.tasks.home.HomePage
import com.example.latarea.ui.theme.LaTareaTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.latarea.ui.activities.tasks.model.TaskViewModel
import com.example.latarea.ui.activities.tasks.model.TaskViewModelFactory
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

@Preview
@Composable
fun CreateTask(navController: NavController) {

    val subjectApi = RetrofitClient.subjectApi
    val factorySubject = SubjectViewModelFactory(subjectApi)
    val subjectViewModel: SubjectViewModel = viewModel(factory = factorySubject)

    val taskApi = RetrofitClient.taskApi
    val factoryTask = TaskViewModelFactory(taskApi)
    val tasksViewModel: TaskViewModel = viewModel(factory = factoryTask)
    HomePage {
        Create(subjectViewModel, tasksViewModel, navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Create(
    subjectViewModel: SubjectViewModel,
    taskViewModel: TaskViewModel,
    navController: NavController
) {

    val context = LocalContext.current
    val subjects = subjectViewModel.subjects.value

    var token by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        token = TokenManager.getToken(context)
        println("Token obtenido: $token")
        token?.let {
            subjectViewModel.loadSubjects(it)
        } ?: println("No se obtuvo token")
    }

    LaTareaTheme {
        var taskTitle by remember { mutableStateOf("") }
        var taskDescription by remember { mutableStateOf("") }
        var selectedColor by remember { mutableStateOf<String?>(null) }
        var selectedSubject by remember { mutableStateOf<Int?>(null) }
        var selectedDate by remember { mutableStateOf<String?>(null) }

        val colorOptions = listOf(
            "#BDD299" to "Verde",
            "#BAA8D6" to "Morado",
            "#EEBAB7" to "Rojo",
            "#EEE1B7" to "Amarillo",
            "#E6B58A" to "Naranja",
            "#D9E3F8" to "Azul"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BasicTextField(
                value = taskTitle,
                onValueChange = { taskTitle = it },
                textStyle = TextStyle(fontSize = 20.sp, color = Color.DarkGray),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                decorationBox = { innerTextField ->
                    if (taskTitle.isEmpty()) {
                        Text(
                            text = "Título de la tarea",
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
                value = taskDescription,
                onValueChange = { taskDescription = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                placeholder = { Text("Agrega una descripción") },
                maxLines = 5,
                singleLine = false,
            )

            DateInput(
                selectedDate = selectedDate.toString(),
                onDateSelected = { selectedDate = it }
            )

            // Selector de color
            Text(
                text = "Asignar un color",
                style = MaterialTheme.typography.labelLarge,
                color = Color.DarkGray
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                colorOptions.forEach { (hex, _) ->
                    ColorOption(
                        color = Color(hex.toColorInt()),
                        isSelected = selectedColor == hex,
                        onSelect = { selectedColor = hex }
                    )
                }
            }

            // Selector de materia
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


            CrearTarea(
                taskViewModel,
                token,
                taskTitle,
                taskDescription,
                selectedColor,
                selectedSubject,
                selectedDate,
                navController
            )

        }
    }
}

@Composable
fun ColorOption(
    color: Color,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color)
            .border(
                width = if (isSelected) 3.dp else 0.dp,
                color = Color.DarkGray,
                shape = CircleShape
            )
            .clickable(onClick = onSelect)
    )
}

@Composable
fun SubjectOption(
    subject: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(if (isSelected) Color.LightGray else Color.Transparent)
            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
            .clickable(onClick = onSelect)
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = subject)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateInput(
    selectedDate: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            onDateSelected("$dayOfMonth/${month + 1}/$year")
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    OutlinedTextField(
        value = selectedDate,
        onValueChange = {},
        label = { Text("Selecciona una fecha") },
        readOnly = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Seleccionar fecha",
                modifier = Modifier.clickable {
                    datePickerDialog.show()
                }
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CrearTarea(
    viewModel: TaskViewModel,
    token: String?,
    taskTitle: String,
    taskDescription: String,
    selectedColor: String?,
    selectedSubject: Int?,
    selectedDate: String?,
    navController: NavController
){

    val today: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    Button(
        onClick = {
            try {
                val formatterInput = DateTimeFormatter.ofPattern("d/M/yyyy")
                val formatterOutput = DateTimeFormatter.ISO_LOCAL_DATE

                val localDate = LocalDate.parse(selectedDate, formatterInput)
                val formattedDate = localDate.format(formatterOutput)

                val displayColor = selectedColor?.removePrefix("#") // Resultado: "FF0000"


                token?.let { tokenValue ->
                    viewModel.createTask(
                        tokenValue,
                        taskTitle,
                        taskDescription,
                        today,
                        formattedDate,
                        displayColor,
                        1,
                        selectedSubject
                    )
                }
                navController.popBackStack()
            } catch (e: Exception) {
                Log.e("CrearTarea", "Error al parsear la fecha: $selectedDate", e)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text = "Guardar", fontSize = 18.sp)
    }
}
