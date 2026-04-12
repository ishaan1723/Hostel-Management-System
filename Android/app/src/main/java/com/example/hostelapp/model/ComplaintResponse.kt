package com.example.hostelapp.model

data class ComplaintResponse(
    val id: Int,
    val title: String,
    val description: String,
    val status: String
)