package com.hostel.hostelmanagementsystem.dto;

public class UserResponse {

    private Long id;
    private String userCode;
    private String fullName;
    private String username;
    private String role;

    public UserResponse(Long id, String userCode, String fullName, String username, String role) {
        this.id = id;
        this.userCode = userCode;
        this.fullName = fullName;
        this.username = username;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getUserCode() {
        return userCode;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
