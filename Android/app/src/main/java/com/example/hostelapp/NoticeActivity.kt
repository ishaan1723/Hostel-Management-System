package com.example.hostelapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelapp.api.ApiService
import com.example.hostelapp.model.Notice
import com.example.hostelapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        recyclerView = findViewById(R.id.noticesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val api = RetrofitClient.getClient().create(ApiService::class.java)

        api.getNotices().enqueue(object : Callback<List<Notice>> {
            override fun onResponse(call: Call<List<Notice>>, response: Response<List<Notice>>) {
                if (response.isSuccessful && response.body() != null) {
                    val notices = response.body()!!
                    if (notices.isEmpty()) {
                        Toast.makeText(this@NoticeActivity, "No notices yet", Toast.LENGTH_SHORT).show()
                    }
                    recyclerView.adapter = NoticeAdapter(notices)
                } else {
                    Toast.makeText(this@NoticeActivity, "Failed to load notices", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Notice>>, t: Throwable) {
                Toast.makeText(this@NoticeActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}