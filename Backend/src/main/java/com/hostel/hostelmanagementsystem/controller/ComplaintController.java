package com.hostel.hostelmanagementsystem.controller;

import com.hostel.hostelmanagementsystem.entity.Complaint;
import com.hostel.hostelmanagementsystem.service.ComplaintService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping("/raise/{userId}")
    public Complaint raiseComplaint(
            @PathVariable Long userId,
            @RequestBody Complaint request) {

        return complaintService.raiseComplaint(
                userId,
                request.getTitle(),
                request.getDescription());
    }

    @GetMapping("/student/{studentId}")
    public List<Complaint> getStudentComplaints(
            @PathVariable Long studentId) {

        return complaintService.getStudentComplaints(studentId);
    }
}