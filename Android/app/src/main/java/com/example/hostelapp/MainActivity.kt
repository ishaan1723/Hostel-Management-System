package com.example.hostelapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hostelapp.api.ApiService
import com.example.hostelapp.model.DashboardResponse
import com.example.hostelapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var usersText: TextView
    lateinit var roomsText: TextView
    lateinit var availableText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Initialize views
        usersText = findViewById(R.id.usersText)
        roomsText = findViewById(R.id.roomsText)
        availableText = findViewById(R.id.availableText)

        // API call
        val apiService = RetrofitClient.getClient().create(ApiService::class.java)

        apiService.getDashboard().enqueue(object : Callback<DashboardResponse> {

            override fun onResponse(
                call: Call<DashboardResponse>,
                response: Response<DashboardResponse>
            ) {
                val data = response.body()

                if (response.isSuccessful && data != null) {
                    usersText.text = "Users: ${data.totalUsers}"
                    roomsText.text = "Rooms: ${data.totalRooms}"
                    availableText.text = "Available: ${data.availableRooms}"
                } else {
                    usersText.text = "Failed to load data"
                }
            }

            override fun onFailure(call: Call<DashboardResponse>, t: Throwable) {
                usersText.text = "Error loading data"
            }
        })
    }
}