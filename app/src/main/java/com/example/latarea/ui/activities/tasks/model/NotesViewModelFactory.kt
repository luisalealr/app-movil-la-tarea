package com.example.latarea.ui.activities.tasks.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.latarea.data.network.NoteApi

class NotesViewModelFactory(private val noteApi: NoteApi) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(noteApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
