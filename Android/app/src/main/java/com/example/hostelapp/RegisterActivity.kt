package com.example.hostelapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.hostelapp.api.ApiService
import com.example.hostelapp.model.User
import com.example.hostelapp.model.UserResponse
import com.example.hostelapp.network.RetrofitClient

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etName = findViewById<EditText>(R.id.etName)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val rgRole = findViewById<RadioGroup>(R.id.rgRole)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {

            val name = etName.text.toString().trim()
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // get selected role
            val selectedRoleId = rgRole.checkedRadioButtonId
            if (selectedRoleId == -1) {
                Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val role = findViewById<RadioButton>(selectedRoleId).text.toString()

            if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(
                fullName = name,
                username = username,
                password = password,
                role = role
            )

            val api = RetrofitClient.getClient().create(ApiService::class.java)

            api.registerUser(user).enqueue(object : Callback<UserResponse> {

                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val newUser = response.body()!!
                        Toast.makeText(
                            this@RegisterActivity,
                            "${newUser.fullName} registered! Code: ${newUser.userCode} ✅",
                            Toast.LENGTH_LONG
                        ).show()
                        finish()
                    } else {
                        val error = response.errorBody()?.string() ?: "Failed: ${response.code()}"
                        Toast.makeText(this@RegisterActivity, error, Toast.LENGTH_LONG).show()
                    }
                     }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Network Error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }
}