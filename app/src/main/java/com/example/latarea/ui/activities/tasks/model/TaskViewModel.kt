package com.example.latarea.ui.activities.tasks.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.latarea.data.model.TaskRequest
import com.example.latarea.data.model.TaskResponse
import com.example.latarea.data.network.RetrofitClient.taskApi
import com.example.latarea.data.network.TaskApi
import kotlinx.coroutines.launch

class TaskViewModel(private val api: TaskApi) : ViewModel() {

    val tasks = mutableStateOf<List<TaskResponse>>(emptyList())
    val errorMessage = mutableStateOf("")

    fun loadTasks(token: String) {
        viewModelScope.launch {
            try {
                val response = api.getTasks("Bearer $token")
                if (response.isSuccessful) {
                    tasks.value = response.body() ?: emptyList()
                    errorMessage.value = ""
                } else {
                    errorMessage.value = "Error al cargar tareas: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Error de red: ${e.message}"
            }
        }
    }

    fun createTask(
        token: String,
        title: String,
        description: String,
        createdAt: String,
        expiresAt: String? = null,
        colorHexa: String? = null,
        priority: Int? = null,
        subjectId: Int? = null,
    ) {
        viewModelScope.launch {
            try {
                val request = TaskRequest(
                    title = title,
                    description = description,
                    createdAt = createdAt,
                    expiresAt = expiresAt,
                    colorHexa = colorHexa,
                    priority = priority,
                    userId = null,
                    subjectId = subjectId
                )
                val response = taskApi.createTask(request, "Bearer $token")
                if (response.isSuccessful) {
                    errorMessage.value = "Esto es para ustedes pa que se lo gocen"
                } else {
                    errorMessage.value = "Error al crear la tarea: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Error de red: ${e.message}"
            }
        }
    }

}
