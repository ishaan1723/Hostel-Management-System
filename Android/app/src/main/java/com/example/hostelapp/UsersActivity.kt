package com.example.hostelapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.hostelapp.api.ApiService
import com.example.hostelapp.model.User
import com.example.hostelapp.network.RetrofitClient

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        recyclerView = findViewById(R.id.usersRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadUsers()
    }

    private fun loadUsers() {

        val apiService = RetrofitClient.getClient().create(ApiService::class.java)

        apiService.getUsers().enqueue(object : Callback<List<User>> {

            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    // ✅ filter only students
                    val users = response.body()!!.filter { it.role == "STUDENT" }
                    if (users.isEmpty()) {
                        Toast.makeText(
                            this@UsersActivity,
                            "No students found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    recyclerView.adapter = UserAdapter(users)
                } else {
                    Toast.makeText(this@UsersActivity, "Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(
                    this@UsersActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}