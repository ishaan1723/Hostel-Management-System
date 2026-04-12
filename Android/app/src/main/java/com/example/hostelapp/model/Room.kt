package com.example.hostelapp.model

data class Room(
    val id: Int = 0,
    val roomNumber: String = "",
    val capacity: Int = 0,
    val occupiedBeds: Int = 0
)