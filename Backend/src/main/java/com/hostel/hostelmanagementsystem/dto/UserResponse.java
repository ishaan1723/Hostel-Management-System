package com.hostel.hostelmanagementsystem.dto;

public class UserResponse {
    private Long id;
    private String userCode;
    private String fullName;
    private String username;
    private String role;
    private Long roomId;
    private String roomNumber;  // ✅ add this

    // constructor without room (login/register)
    public UserResponse(Long id, String userCode, String fullName, String username, String role) {
        this.id = id;
        this.userCode = userCode;
        this.fullName = fullName;
        this.username = username;
        this.role = role;
        this.roomId = null;
        this.roomNumber = null;
    }

    // constructor with room (getAllUsers)
    public UserResponse(Long id, String userCode, String fullName, String username, String role, Long roomId, String roomNumber) {
        this.id = id;
        this.userCode = userCode;
        this.fullName = fullName;
        this.username = username;
        this.role = role;
        this.roomId = roomId;
        this.roomNumber = roomNumber;
    }

    public Long getId() { return id; }
    public String getUserCode() { return userCode; }
    public String getFullName() { return fullName; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
    public Long getRoomId() { return roomId; }
    public String getRoomNumber() { return roomNumber; }
}