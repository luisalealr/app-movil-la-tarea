package com.example.latarea.ui.activities.createSubject.views

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.latarea.data.model.SubjectResponse
import com.example.latarea.data.network.SubjectApi
import kotlinx.coroutines.launch

class SubjectViewModel(private val api: SubjectApi) : ViewModel() {

    val subjects = mutableStateOf<List<SubjectResponse>>(emptyList())
    val errorMessage = mutableStateOf("")

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
}