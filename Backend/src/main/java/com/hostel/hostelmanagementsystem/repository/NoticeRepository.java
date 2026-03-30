package com.hostel.hostelmanagementsystem.repository;

import com.hostel.hostelmanagementsystem.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}