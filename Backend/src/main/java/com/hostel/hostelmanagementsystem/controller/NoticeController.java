package com.hostel.hostelmanagementsystem.controller;

import com.hostel.hostelmanagementsystem.entity.Notice;
import com.hostel.hostelmanagementsystem.repository.NoticeRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeRepository noticeRepository;

    public NoticeController(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @PostMapping
    public Notice createNotice(@RequestBody Notice notice) {

        notice.setCreatedAt(LocalDateTime.now());

        return noticeRepository.save(notice);
    }

    @GetMapping
    public List<Notice> getAllNotices() {

        return noticeRepository.findAll();
    }
}