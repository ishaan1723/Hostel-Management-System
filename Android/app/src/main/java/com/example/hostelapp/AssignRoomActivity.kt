package com.example.hostelapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.hostelapp.api.ApiService
import com.example.hostelapp.model.Room
import com.example.hostelapp.model.User
import com.example.hostelapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssignRoomActivity : AppCompatActivity() {

    private var userList: List<User> = emptyList()
    private var roomList: List<Room> = emptyList()
    private var selectedUserId: Long = -1
    private var selectedRoomId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assign_room)

        val spinnerStudents = findViewById<Spinner>(R.id.spinnerStudents)
        val spinnerRooms = findViewById<Spinner>(R.id.spinnerRooms)
        val btnAssign = findViewById<Button>(R.id.btnAssign)

        val api = RetrofitClient.getClient().create(ApiService::class.java)

        // Load students
        api.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful && response.body() != null) {
                    // ✅ only unassigned students
                    userList = response.body()!!.filter {
                        it.role == "STUDENT" && it.roomId == null
                    }
                    if (userList.isEmpty()) {
                        Toast.makeText(
                            this@AssignRoomActivity,
                            "No unassigned students found!",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }
                    val names = userList.map { "${it.fullName} (${it.username})" }
                    val adapter = ArrayAdapter(
                        this@AssignRoomActivity,
                        android.R.layout.simple_spinner_item,
                        names
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerStudents.adapter = adapter

                    spinnerStudents.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, pos: Int, id: Long) {
                            selectedUserId = userList[pos].id.toLong()
                        }
                        override fun onNothingSelected(parent: AdapterView<*>) {}
                    }
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(this@AssignRoomActivity, "Failed to load students", Toast.LENGTH_SHORT).show()
            }
        })

        // Load rooms
        api.getRooms().enqueue(object : Callback<List<Room>> {
            override fun onResponse(call: Call<List<Room>>, response: Response<List<Room>>) {
                if (response.isSuccessful && response.body() != null) {
                    // ✅ only rooms that are not full
                    roomList = response.body()!!.filter {
                        it.occupiedBeds < it.capacity
                    }
                    if (roomList.isEmpty()) {
                        Toast.makeText(
                            this@AssignRoomActivity,
                            "No available rooms!",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }
                    val roomNames = roomList.map {
                        "Room ${it.roomNumber} (${it.capacity - it.occupiedBeds} beds free)"
                    }
                    val adapter = ArrayAdapter(
                        this@AssignRoomActivity,
                        android.R.layout.simple_spinner_item,
                        roomNames
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerRooms.adapter = adapter

                    spinnerRooms.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, pos: Int, id: Long) {
                            selectedRoomId = roomList[pos].id.toLong()
                        }
                        override fun onNothingSelected(parent: AdapterView<*>) {}
                    }
                }
            }
            override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                Toast.makeText(this@AssignRoomActivity, "Failed to load rooms", Toast.LENGTH_SHORT).show()
            }
        })

        // Assign button
        btnAssign.setOnClickListener {
            if (selectedUserId == -1L || selectedRoomId == -1L) {
                Toast.makeText(this, "Please select both student and room", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            api.assignRoom(selectedUserId, selectedRoomId).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@AssignRoomActivity,
                            "Room assigned successfully ✅",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        val error = response.errorBody()?.string() ?: "Failed: ${response.code()}"
                        Toast.makeText(this@AssignRoomActivity, error, Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@AssignRoomActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}