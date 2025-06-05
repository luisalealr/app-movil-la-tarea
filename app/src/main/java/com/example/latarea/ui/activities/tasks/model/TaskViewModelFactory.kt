package com.example.latarea.ui.activities.tasks.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.latarea.data.network.TaskApi

class TaskViewModelFactory(private val taskApi: TaskApi) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(taskApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
