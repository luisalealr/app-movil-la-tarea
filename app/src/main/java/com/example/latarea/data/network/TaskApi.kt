package com.example.latarea.data.network

import com.example.latarea.data.model.TaskRequest
import com.example.latarea.data.model.TaskResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TaskApi {
    @GET("/api/task/list")
    suspend fun getTasks(@Header("Authorization") authToken: String): Response<List<TaskResponse>>

    @POST("/api/task/create")
    suspend fun createTask(@Body task: TaskRequest, @Header("Authorization") token: String): Response<String>

}