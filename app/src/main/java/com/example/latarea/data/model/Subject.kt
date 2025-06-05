package com.example.latarea.data.model

data class SubjectResponse(
    val id: Int,
    val title: String,
    val colorHexa: String? = null,
    val userId: String? = null,
    val scheduleBlockId: Int? = null,
)

data class SubjectRequest(
    val title: String,
    val colorHexa: String? = null,
    val userId: String? = null,
    val scheduleBlockId: Int? = null,
)