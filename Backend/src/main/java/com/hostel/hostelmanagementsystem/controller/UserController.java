package com.hostel.hostelmanagementsystem.controller;

import com.hostel.hostelmanagementsystem.dto.UserResponse;
import com.hostel.hostelmanagementsystem.entity.User;
import com.hostel.hostelmanagementsystem.repository.UserRepository;
import com.hostel.hostelmanagementsystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public UserResponse registerUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return new UserResponse(
                savedUser.getId(),
                savedUser.getUserCode(),
                savedUser.getFullName(),
                savedUser.getUsername(),
                savedUser.getRole()
        );
    }

    @PostMapping("/login")
    public UserResponse loginUser(@RequestBody User user) {
        User loggedInUser = userService.loginUser(user.getUsername(), user.getPassword());
        return new UserResponse(
                loggedInUser.getId(),
                loggedInUser.getUserCode(),
                loggedInUser.getFullName(),
                loggedInUser.getUsername(),
                loggedInUser.getRole()
        );
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getUserCode(),
                        u.getFullName(),
                        u.getUsername(),
                        u.getRole(),
                        u.getRoom() != null ? u.getRoom().getId() : null,
                        u.getRoom() != null ? u.getRoom().getRoomNumber() : null  // ✅
                ))
                .collect(Collectors.toList());
    }
    @GetMapping("/count")
    public long totalUsers() {
        return userRepository.count();
    }
}