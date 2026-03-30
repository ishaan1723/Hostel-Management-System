package com.hostel.hostelmanagementsystem.service;

import com.hostel.hostelmanagementsystem.entity.Room;
import com.hostel.hostelmanagementsystem.entity.User;
import com.hostel.hostelmanagementsystem.repository.RoomRepository;
import com.hostel.hostelmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public RoomService(RoomRepository roomRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    public Room addRoom(Room room) {
        room.setOccupiedBeds(0);
        return roomRepository.save(room);
    }

    public void assignStudentToRoom(Long userId, Long roomId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (!user.getRole().equalsIgnoreCase("STUDENT")) {
            throw new RuntimeException("Only students can be assigned rooms!");
        }

        if (user.getRoom() != null) {
            throw new RuntimeException("Student already assigned to a room!");
        }

        if (room.getOccupiedBeds() >= room.getCapacity()) {
            throw new RuntimeException("Room is full!");
        }

        room.setOccupiedBeds(room.getOccupiedBeds() + 1);
        user.setRoom(room);

        roomRepository.save(room);
        userRepository.save(user);   // VERY IMPORTANT
    }
    public void removeStudentFromRoom(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRoom() == null) {
            throw new RuntimeException("Student is not assigned to any room!");
        }

        Room room = user.getRoom();

        room.setOccupiedBeds(room.getOccupiedBeds() - 1);
        user.setRoom(null);

        userRepository.save(user);
        roomRepository.save(room);
    }
    public Room getRoomById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
    public void transferStudent(Long userId, Long newRoomId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRoom() == null) {
            throw new RuntimeException("Student is not assigned to any room");
        }

        Room oldRoom = user.getRoom();

        Room newRoom = roomRepository.findById(newRoomId)
                .orElseThrow(() -> new RuntimeException("New room not found"));

        if (newRoom.getOccupiedBeds() >= newRoom.getCapacity()) {
            throw new RuntimeException("New room is full");
        }

        // decrease old room count
        oldRoom.setOccupiedBeds(oldRoom.getOccupiedBeds() - 1);

        // increase new room count
        newRoom.setOccupiedBeds(newRoom.getOccupiedBeds() + 1);

        // assign new room
        user.setRoom(newRoom);

        roomRepository.save(oldRoom);
        roomRepository.save(newRoom);
        userRepository.save(user);
    }

}
