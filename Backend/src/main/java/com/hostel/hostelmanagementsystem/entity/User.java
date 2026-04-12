package com.hostel.hostelmanagementsystem.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userCode;
    private String fullName;
    private String username;
    private String password;
    private String role;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private Room room;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUserCode() { return userCode; }
    public void setUserCode(String userCode) { this.userCode = userCode; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }
}