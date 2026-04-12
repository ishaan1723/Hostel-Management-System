package com.example.hostelapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelapp.api.ApiService
import com.example.hostelapp.model.ComplaintResponse
import com.example.hostelapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewComplaintsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_complaints)

        recyclerView = findViewById(R.id.complaintsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val prefs = getSharedPreferences("hostel_prefs", MODE_PRIVATE)
        val userId = prefs.getLong("userId", -1)

        if (userId == -1L) {
            Toast.makeText(this, "Please login again", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val api = RetrofitClient.getClient().create(ApiService::class.java)

        api.getComplaints(userId).enqueue(object : Callback<List<ComplaintResponse>> {
            override fun onResponse(call: Call<List<ComplaintResponse>>, response: Response<List<ComplaintResponse>>) {
                if (response.isSuccessful && response.body() != null) {
                    val complaints = response.body()!!
                    if (complaints.isEmpty()) {
                        Toast.makeText(this@ViewComplaintsActivity, "No complaints found", Toast.LENGTH_SHORT).show()
                    }
                    recyclerView.adapter = ComplaintAdapter(complaints)
                } else {
                    Toast.makeText(this@ViewComplaintsActivity, "Failed to load", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<ComplaintResponse>>, t: Throwable) {
                Toast.makeText(this@ViewComplaintsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}