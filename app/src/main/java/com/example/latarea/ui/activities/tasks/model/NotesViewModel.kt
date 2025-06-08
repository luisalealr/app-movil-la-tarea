package com.example.latarea.ui.activities.tasks.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.latarea.data.model.NoteRequest
import com.example.latarea.data.model.NoteResponse
import com.example.latarea.data.network.NoteApi
import kotlinx.coroutines.launch
import com.example.latarea.data.network.RetrofitClient.noteApi


class NotesViewModel(private val api: NoteApi) : ViewModel() {

    val notes = mutableStateOf<List<NoteResponse>>(emptyList())
    val errorMessage = mutableStateOf("")
    val selectedNote = mutableStateOf<NoteResponse?>(null)

    fun loadNotes(token: String) {
        viewModelScope.launch {
            try {
                val response = api.getNotes("Bearer $token")
                if (response.isSuccessful) {
                    notes.value = response.body() ?: emptyList()
                    errorMessage.value = ""
                } else {
                    errorMessage.value = "Error al cargar notas: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Error de red: ${e.message}"
            }
        }
    }

    fun createNote(
        token: String,
        title: String,
        description: String,
    ){
        viewModelScope.launch {
            try{
                val request = NoteRequest(
                    title = title,
                    description = description,
                    userId = null,
                )
                val response = noteApi.createNote(request, "Bearer $token")
                if (response.isSuccessful) {
                    errorMessage.value = "Se creó la nota con éxito"
                } else {
                    errorMessage.value = "Error al crear la nota: ${response.code()}"
                }
            } catch (e: Exception){
                errorMessage.value = "Error de red: ${e.message}"
            }
        }
    }

    fun deleteNote(noteId: Int, token: String) {
        viewModelScope.launch {
            try {
                val response = noteApi.deleteNote(noteId, "Bearer $token")
                if (response.isSuccessful) {
                    errorMessage.value = "Se eliminó la nota correctamente"
                } else {
                    errorMessage.value = "Error al eliminar la nota: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Error de red: ${e.message}"
            }
        }
    }

    fun getNoteById(taskId: Int, token: String) {
        viewModelScope.launch {
            try {
                val response = api.getNoteById(taskId, "Bearer $token")
                if (response.isSuccessful) {
                    selectedNote.value = response.body()
                    errorMessage.value = ""
                } else {
                    errorMessage.value = "Error al cargar la nota: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Error de red: ${e.message}"
            }
        }
    }

}