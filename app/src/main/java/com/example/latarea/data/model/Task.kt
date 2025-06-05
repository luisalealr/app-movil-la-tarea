package com.example.latarea.data.model

data class TaskResponse(
    val id: Int,
    val title: String,
    val description: String,
    val createdAt: String,
    val expiresAt: String? = null,
    val colorHexa: String? = null,
    val priority: Int? = null,
    val userId: String?= null,
    val subjectId: Int? = null
)

data class TaskRequest(
    val title: String,
    val description: String,
    val createdAt: String,
    val expiresAt: String? = null,
    val colorHexa: String? = null,
    val priority: Int? = null,
    val userId: String?= null,
    val subjectId: Int? = null
)