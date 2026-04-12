package com.example.hostelapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.hostelapp.api.ApiService
import com.example.hostelapp.model.Room
import com.example.hostelapp.network.RetrofitClient

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoomsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms)

        recyclerView = findViewById(R.id.roomsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadRooms()
    }

    private fun loadRooms() {

        val apiService = RetrofitClient.getClient()
            .create(ApiService::class.java)

        apiService.getRooms().enqueue(object : Callback<List<Room>> {

            override fun onResponse(
                call: Call<List<Room>>,
                response: Response<List<Room>>
            ) {
                if (response.isSuccessful && response.body() != null) {

                    val rooms = response.body()!!
                    recyclerView.adapter = RoomAdapter(rooms)

                } else {
                    Toast.makeText(this@RoomsActivity, "Failed to load", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                Toast.makeText(this@RoomsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}