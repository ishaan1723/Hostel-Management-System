package com.hostel.hostelmanagementsystem.dto;

public class DashboardDTO {

    private long totalUsers;
    private long totalRooms;
    private long availableRooms;

    public DashboardDTO(long totalUsers, long totalRooms, long availableRooms) {
        this.totalUsers = totalUsers;
        this.totalRooms = totalRooms;
        this.availableRooms = availableRooms;
    }

    public long getTotalUsers() { return totalUsers; }
    public long getTotalRooms() { return totalRooms; }
    public long getAvailableRooms() { return availableRooms; }
}