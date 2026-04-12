package com.example.hostelapp

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.example.hostelapp.api.ApiService
import com.example.hostelapp.model.DashboardResponse
import com.example.hostelapp.network.RetrofitClient

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var usersText: TextView
    private lateinit var roomsText: TextView
    private lateinit var availableText: TextView
    private lateinit var refreshBtn: TextView
    private lateinit var btnLogout: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bind stat views
        usersText = findViewById(R.id.usersText)
        roomsText = findViewById(R.id.roomsText)
        availableText = findViewById(R.id.availableText)
        refreshBtn = findViewById(R.id.refreshBtn)
        btnLogout = findViewById(R.id.btnLogout)

        // Bind navigation buttons
        val btnRooms = findViewById<LinearLayout>(R.id.btnRooms)
        val btnUsers = findViewById<LinearLayout>(R.id.btnUsers)
        val btnAssignRoom = findViewById<LinearLayout>(R.id.btnAssignRoom)
        val btnAddRoom = findViewById<LinearLayout>(R.id.btnAddRoom)
        val btnRegister = findViewById<LinearLayout>(R.id.btnRegister)
        val btnComplaints = findViewById<LinearLayout>(R.id.btnComplaints)
        val btnViewComplaints = findViewById<LinearLayout>(R.id.btnViewComplaints)
        val btnNotices = findViewById<LinearLayout>(R.id.btnNotices)

        // Navigation click listeners
        btnRooms.setOnClickListener {
            startActivity(Intent(this, RoomsActivity::class.java))
        }

        btnUsers.setOnClickListener {
            startActivity(Intent(this, UsersActivity::class.java))
        }

        btnAssignRoom.setOnClickListener {
            startActivity(Intent(this, AssignRoomActivity::class.java))
        }

        btnAddRoom.setOnClickListener {
            startActivity(Intent(this, AddRoomActivity::class.java))
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnComplaints.setOnClickListener {
            startActivity(Intent(this, RaiseComplaintActivity::class.java))
        }

        btnViewComplaints.setOnClickListener {
            startActivity(Intent(this, ViewComplaintsActivity::class.java))
        }

        btnNotices.setOnClickListener {
            startActivity(Intent(this, NoticeActivity::class.java))
        }

        // Refresh
        refreshBtn.setOnClickListener {
            loadDashboard()
        }

        // Logout
        btnLogout.setOnClickListener {
            val prefs = getSharedPreferences("hostel_prefs", MODE_PRIVATE)
            prefs.edit().clear().apply()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // Load dashboard on start
        loadDashboard()
    }

    private fun loadDashboard() {
        val apiService = RetrofitClient.getClient().create(ApiService::class.java)

        apiService.getDashboard().enqueue(object : Callback<DashboardResponse> {

            override fun onResponse(
                call: Call<DashboardResponse>,
                response: Response<DashboardResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!
                    usersText.text = "${data.totalUsers}"
                    roomsText.text = "${data.totalRooms}"
                    availableText.text = "${data.availableRooms}"
                } else {
                    usersText.text = "!"
                }
            }

            override fun onFailure(call: Call<DashboardResponse>, t: Throwable) {
                usersText.text = "!"
            }
        })
    }
}