package com.example.hostelapp.api;

import com.example.hostelapp.model.ComplaintRequest;
import com.example.hostelapp.model.ComplaintResponse;
import com.example.hostelapp.model.DashboardResponse;
import com.example.hostelapp.model.Notice;
import com.example.hostelapp.model.User;
import com.example.hostelapp.model.UserResponse;
import com.example.hostelapp.model.Room;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("dashboard")
    Call<DashboardResponse> getDashboard();

    @GET("rooms")
    Call<List<Room>> getRooms();

//    @GET("users")  // ✅ fixed endpoint
//    Call<List<User>> getUsers();
    @POST("login")
    Call<UserResponse> loginUser(@Body User user);

    @POST("register")
    Call<UserResponse> registerUser(@Body User user);

    @POST("rooms/assign")
    Call<Void> assignRoom(@Query("userId") long userId, @Query("roomId") long roomId);

    @GET("users")
    Call<List<User>> getUsers();
    @POST("rooms/add")
    Call<Room> addRoom(@Body Room room);

    @POST("api/complaints/raise/{userId}")
    Call<ComplaintResponse> raiseComplaint(@Path("userId") long userId, @Body ComplaintRequest complaint);
    @GET("api/complaints/student/{studentId}")
    Call<List<ComplaintResponse>> getComplaints(@Path("studentId") long studentId);
    @GET("api/notices")
    Call<List<Notice>> getNotices();
}