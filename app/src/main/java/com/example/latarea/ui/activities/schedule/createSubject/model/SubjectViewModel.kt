package com.example.latarea.ui.activities.schedule.createSubject.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.latarea.data.model.SubjectResponse
import com.example.latarea.data.network.SubjectApi
import kotlinx.coroutines.launch

class SubjectViewModel(private val api: SubjectApi) : ViewModel() {

    val subjects = mutableStateOf<List<SubjectResponse>>(emptyList())
    val errorMessage = mutableStateOf("")
    val selectedSubject = mutableStateOf<SubjectResponse?>(null)

    fun loadSubjects(token: String) {
        viewModelScope.launch {
            try {
                val response = api.getSubjects("Bearer $token")
                if (response.isSuccessful) {
                    subjects.value = response.body() ?: emptyList()
                    errorMessage.value = ""
                } else {
                    errorMessage.value = "Error al cargar las materias: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Error de red: ${e.message}"
            }
        }
    }

    fun getSubjectById(subjectId: Int?, token: String) {
        viewModelScope.launch {
            try {
                val response = api.getSubjectById(subjectId, "Bearer $token")
                if (response.isSuccessful) {
                    selectedSubject.value = response.body()
                    errorMessage.value = ""
                } else {
                    errorMessage.value = "Error al cargar la tarea: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Error de red: ${e.message}"
            }
        }
    }
}