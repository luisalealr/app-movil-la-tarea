package com.example.latarea.data.network

import com.example.latarea.data.model.TaskRequest
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

interface TaskApi {
    @GET("/api/task/list")
    suspend fun getTasks(@Header("Authorization") authToken: String): Response<List<TaskResponse>>

    @POST("/api/task/create")
    suspend fun createTask(
        @Body task: TaskRequest,
        @Header("Authorization") token: String
    ): Response<String>

    @DELETE("api/task/{id}")
    suspend fun deleteTask(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<String>

    @GET("api/task/{id}")
    suspend fun getTaskById(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<TaskResponse>

    @PUT("/api/task/update/{id}")
    suspend fun updateTask(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body taskUpdate: TaskUpdate
    ): Response<String>

}