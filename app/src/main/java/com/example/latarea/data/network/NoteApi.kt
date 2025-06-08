package com.example.latarea.data.network

import com.example.latarea.data.model.NoteRequest
import com.example.latarea.data.model.NoteResponse
import com.example.latarea.data.model.TaskResponse
import com.example.latarea.data.model.TaskUpdate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NoteApi {

    @GET("/api/note/list")
    suspend fun getNotes(@Header("Authorization") authToken: String): Response<List<NoteResponse>>

    @POST("/api/note/create")
    suspend fun createNote(
        @Body note: NoteRequest,
        @Header("Authorization") token: String
    ): Response<String>

    @DELETE("/api/note/{id}")
    suspend fun deleteNote(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<String>

    @GET("api/note/{id}")
    suspend fun getNoteById(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<NoteResponse>

}