package com.hostel.hostelmanagementsystem.repository;

import com.hostel.hostelmanagementsystem.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}