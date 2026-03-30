package com.hostel.hostelmanagementsystem.controller;
import com.hostel.hostelmanagementsystem.dto.DashboardDTO;
import com.hostel.hostelmanagementsystem.service.DashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardDTO getDashboard() {
        return dashboardService.getDashboardData();
    }
}