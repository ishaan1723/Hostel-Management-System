package com.example.hostelapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.hostelapp.api.ApiService
import com.example.hostelapp.model.User
import com.example.hostelapp.model.UserResponse
import com.example.hostelapp.network.RetrofitClient

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {

            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(
                fullName = "",
                username = username,
                password = password,
                role = ""
            )

            val api = RetrofitClient.getClient().create(ApiService::class.java)

            api.loginUser(user).enqueue(object : Callback<UserResponse> {

                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val loggedInUser = response.body()!!
                        Toast.makeText(
                            this@LoginActivity,
                            "Welcome ${loggedInUser.fullName}! ✅",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        val error = response.errorBody()?.string() ?: "Login failed"
                        Toast.makeText(this@LoginActivity, error, Toast.LENGTH_LONG).show()
                    }
                    if (response.isSuccessful && response.body() != null) {
                        val loggedInUser = response.body()!!
                        Toast.makeText(
                            this@LoginActivity,
                            "Welcome ${loggedInUser.fullName}! ✅",
                            Toast.LENGTH_SHORT
                        ).show()
                        // ADD THIS LINE HERE ↓
                        val prefs = getSharedPreferences("hostel_prefs", MODE_PRIVATE)
                        prefs.edit().putLong("userId", loggedInUser.id.toLong()).apply()
                        // ↑ ADD TILL HERE
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }

                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Network Error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }
}