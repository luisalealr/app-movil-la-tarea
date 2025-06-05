package com.example.latarea.data.model

data class LoginRequest(
    val token: String
)

data class RegisterRequest(
    val token: String,
    val name: String
)

data class AuthResponse(
    val token: String
)
