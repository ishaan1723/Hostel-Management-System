package com.example.hostelapp.model;

public class DashboardResponse {

    private long totalUsers;
    private long totalRooms;
    private long availableRooms;

    public long getTotalUsers() { return totalUsers; }
    public long getTotalRooms() { return totalRooms; }
    public long getAvailableRooms() { return availableRooms; }
}