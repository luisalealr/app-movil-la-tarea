package com.example.latarea.data.network

import com.example.latarea.data.model.NoteRequest
import com.example.latarea.data.model.NoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface NoteApi {

    @GET("/api/note/list")
    suspend fun getNotes(@Header("Authorization") authToken: String): Response<List<NoteResponse>>

    @POST("/api/note/create")
    suspend fun createNote(@Body note: NoteRequest, @Header("Authorization") token: String): Response<String>
}