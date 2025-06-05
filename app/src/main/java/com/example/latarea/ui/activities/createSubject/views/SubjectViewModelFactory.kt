package com.example.latarea.ui.activities.createSubject.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.latarea.data.network.SubjectApi

class SubjectViewModelFactory(private val subjectApi: SubjectApi) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SubjectViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SubjectViewModel(subjectApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}