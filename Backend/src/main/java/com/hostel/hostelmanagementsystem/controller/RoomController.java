package com.hostel.hostelmanagementsystem.controller;

import com.hostel.hostelmanagementsystem.entity.Room;
import com.hostel.hostelmanagementsystem.repository.RoomRepository;
import com.hostel.hostelmanagementsystem.service.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomRepository roomRepository;
    private final RoomService roomService;

    public RoomController(RoomService roomService, RoomRepository roomRepository) {
        this.roomService = roomService;
        this.roomRepository = roomRepository;
    }
    @PostMapping("/add")
    public Room addRoom(@RequestBody Room room) {
        return roomService.addRoom(room);
    }

    @PostMapping("/assign")
    public String assignStudent(@RequestParam Long userId,
                                @RequestParam Long roomId) {

        roomService.assignStudentToRoom(userId, roomId);
        return "Student assigned successfully!";
    }
    @PostMapping("/remove")
    public String removeStudent(@RequestParam Long userId) {

        roomService.removeStudentFromRoom(userId);
        return "Student removed from room successfully!";
    }
    @GetMapping("/{roomId}")
    public Room getRoom(@PathVariable Long roomId) {
        return roomService.getRoomById(roomId);
    }
    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @PostMapping("/transfer")
    public String transferStudent(
            @RequestParam Long userId,
            @RequestParam Long newRoomId) {

        roomService.transferStudent(userId, newRoomId);
        return "Student transferred successfully";

    }
    @GetMapping("/count")
    public long totalRooms(){
        return roomRepository.count();
    }

}
