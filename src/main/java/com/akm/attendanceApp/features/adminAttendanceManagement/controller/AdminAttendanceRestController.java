package com.akm.attendanceApp.features.adminAttendanceManagement.controller;

import com.akm.attendanceApp.features.adminAttendanceManagement.dto.response.AttendanceResponse;
import com.akm.attendanceApp.features.adminAttendanceManagement.service.AdminAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/attendance")
@RequiredArgsConstructor
public class AdminAttendanceRestController {

    @Autowired
    private final AdminAttendanceService adminAttendanceService;

    @GetMapping("/list")
    public ResponseEntity<List<AttendanceResponse>> getAllUserAttendance() {

        List<AttendanceResponse> attendances = adminAttendanceService.getAllUsersAttendances();

        return new ResponseEntity<>(attendances, HttpStatus.OK);
    }
}
