package com.example.latarea.data.network

import com.example.latarea.data.model.SubjectResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface SubjectApi {

    @GET("/api/subject/list")
    suspend fun getSubjects(@Header("Authorization") authToken: String): Response<List<SubjectResponse>>

}