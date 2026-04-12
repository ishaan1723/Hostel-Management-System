package com.example.hostelapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hostelapp.api.ApiService
import com.example.hostelapp.model.Room
import com.example.hostelapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddRoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_room)

        val etRoomNumber = findViewById<EditText>(R.id.etRoomNumber)
        val etCapacity = findViewById<EditText>(R.id.etCapacity)
        val btnAddRoom = findViewById<Button>(R.id.btnAddRoom)

        btnAddRoom.setOnClickListener {

            val roomNumber = etRoomNumber.text.toString().trim()
            val capacityStr = etCapacity.text.toString().trim()

            if (roomNumber.isEmpty() || capacityStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val room = Room(
                id = 0,
                roomNumber = roomNumber,
                capacity = capacityStr.toInt(),
                occupiedBeds = 0
            )
            val api = RetrofitClient.getClient().create(ApiService::class.java)

            api.addRoom(room).enqueue(object : Callback<Room> {
                override fun onResponse(call: Call<Room>, response: Response<Room>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@AddRoomActivity,
                            "Room ${roomNumber} added successfully ✅",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        val error = response.errorBody()?.string() ?: "Failed: ${response.code()}"
                        Toast.makeText(this@AddRoomActivity, error, Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<Room>, t: Throwable) {
                    Toast.makeText(this@AddRoomActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}