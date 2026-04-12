package com.example.hostelapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hostelapp.api.ApiService
import com.example.hostelapp.model.ComplaintRequest
import com.example.hostelapp.model.ComplaintResponse
import com.example.hostelapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RaiseComplaintActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_raise_complaint)

        val etTitle = findViewById<EditText>(R.id.etComplaintTitle)
        val etDesc = findViewById<EditText>(R.id.etComplaintDesc)
        val btnSubmit = findViewById<Button>(R.id.btnSubmitComplaint)

        // Get userId from SharedPreferences (we'll set this on login)
        val prefs = getSharedPreferences("hostel_prefs", MODE_PRIVATE)
        val userId = prefs.getLong("userId", -1)

        btnSubmit.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val desc = etDesc.text.toString().trim()

            if (title.isEmpty() || desc.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (userId == -1L) {
                Toast.makeText(this, "Please login again", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val complaint = ComplaintRequest(title = title, description = desc)
            val api = RetrofitClient.getClient().create(ApiService::class.java)

            api.raiseComplaint(userId, complaint).enqueue(object : Callback<ComplaintResponse> {
                override fun onResponse(call: Call<ComplaintResponse>, response: Response<ComplaintResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@RaiseComplaintActivity, "Complaint raised ✅", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        val error = response.errorBody()?.string() ?: "Failed: ${response.code()}"
                        Toast.makeText(this@RaiseComplaintActivity, error, Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<ComplaintResponse>, t: Throwable) {
                    Toast.makeText(this@RaiseComplaintActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}