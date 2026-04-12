package com.example.hostelapp.model

data class UserResponse(
    val id: Int,
    val userCode: String,
    val fullName: String,
    val username: String,
    val role: String
)