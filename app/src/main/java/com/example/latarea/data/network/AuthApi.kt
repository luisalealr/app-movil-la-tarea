package com.example.latarea.data.network

import com.example.latarea.data.model.AuthResponse
import com.example.latarea.data.model.LoginRequest
import com.example.latarea.data.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("api/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>
}
