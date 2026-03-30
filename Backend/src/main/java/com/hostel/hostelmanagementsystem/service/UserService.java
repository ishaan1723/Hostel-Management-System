package com.hostel.hostelmanagementsystem.service;

import com.hostel.hostelmanagementsystem.entity.User;
import com.hostel.hostelmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {

        // check if username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken. Please choose another.");
        }

        String prefix;

        if (user.getRole().equalsIgnoreCase("STUDENT")) {
            prefix = "STU";
        } else if (user.getRole().equalsIgnoreCase("WARDEN")) {
            prefix = "WAR";
        } else {
            prefix = "ADM";
        }

        String uniqueNumber = String.valueOf(System.currentTimeMillis()).substring(8);
        String userCode = prefix + uniqueNumber;

        user.setUserCode(userCode);

        return userRepository.save(user);
    }
    public User loginUser(String username, String password) {

        return userRepository
                .findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
