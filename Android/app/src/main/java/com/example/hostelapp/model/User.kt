package com.example.hostelapp.model
data class User(
    val id: Int = 0,
    val fullName: String = "",
    val username: String = "",
    val password: String = "",
    val role: String = "",
    val roomId: Int? = null,
    val roomNumber: String? = null  // ✅ add this
)