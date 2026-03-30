package com.hostel.hostelmanagementsystem.service;

import com.hostel.hostelmanagementsystem.dto.DashboardDTO;
import com.hostel.hostelmanagementsystem.repository.RoomRepository;
import com.hostel.hostelmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;
@Service
public class DashboardService {

    private final UserRepository userRepository;
        private final RoomRepository roomRepository;

        public DashboardService(UserRepository userRepository,
                                RoomRepository roomRepository) {
            this.userRepository = userRepository;
            this.roomRepository = roomRepository;
        }

        public DashboardDTO getDashboardData() {

            long totalUsers = userRepository.count();
            long totalRooms = roomRepository.count();

            long availableRooms = roomRepository.findAll()
                    .stream()
                    .filter(r -> r.getStudents().size() < r.getCapacity())
                    .count();

            return new DashboardDTO(totalUsers, totalRooms, availableRooms);
        }
    }

