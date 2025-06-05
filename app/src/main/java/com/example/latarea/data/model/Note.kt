package com.example.latarea.data.model

data class NoteResponse (
    val id: Int,
    val title: String,
    val description: String,
    val userId : String? = null,
)

data class NoteRequest (
    val title: String,
    val description: String,
    val userId : String? = null,
)