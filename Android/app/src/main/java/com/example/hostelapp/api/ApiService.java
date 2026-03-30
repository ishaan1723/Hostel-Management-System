
package com.example.hostelapp.api;
import com.example.hostelapp.model.DashboardResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/dashboard")
    Call<DashboardResponse> getDashboard();
}