package com.hostel.hostelmanagementsystem.service;

import com.hostel.hostelmanagementsystem.entity.Complaint;
import com.hostel.hostelmanagementsystem.entity.User;
import com.hostel.hostelmanagementsystem.repository.ComplaintRepository;
import com.hostel.hostelmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

    public ComplaintService(ComplaintRepository complaintRepository,
                            UserRepository userRepository) {
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
    }

    public Complaint raiseComplaint(Long userId, String title, String description) {
        User student = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Complaint complaint = new Complaint();
        complaint.setTitle(title);
        complaint.setDescription(description);
        complaint.setStatus("OPEN");
        complaint.setCreatedAt(LocalDateTime.now());
        complaint.setStudent(student);

        return complaintRepository.save(complaint);
    }

    public List<Complaint> getStudentComplaints(Long studentId) {
        return complaintRepository.findByStudentId(studentId);
    }
}