package com.hostel.hostelmanagementsystem.controller;

import com.hostel.hostelmanagementsystem.entity.User;
import com.hostel.hostelmanagementsystem.repository.UserRepository;
import com.hostel.hostelmanagementsystem.service.UserService;
import com.hostel.hostelmanagementsystem.dto.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/count")
    public long totalUsers(){
        return userRepository.count();
    }
}
