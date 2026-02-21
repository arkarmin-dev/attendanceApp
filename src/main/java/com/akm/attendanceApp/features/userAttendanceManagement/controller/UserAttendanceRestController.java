package com.akm.attendanceApp.features.userAttendanceManagement.controller;

import com.akm.attendanceApp.data.enums.AttendanceType;
import com.akm.attendanceApp.data.models.User;
import com.akm.attendanceApp.features.userAttendanceManagement.dto.response.UserAttendanceResponse;
import com.akm.attendanceApp.features.userAttendanceManagement.service.UserAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/attendance")
@RequiredArgsConstructor
public class UserAttendanceRestController {

    @Autowired
    private final UserAttendanceService userAttendanceService;

    @GetMapping("/list")
    public ResponseEntity<List<UserAttendanceResponse>> getAllAttendances(@AuthenticationPrincipal User user) {
        List<UserAttendanceResponse> attendances = userAttendanceService.getAllAttendances(user);

        return new ResponseEntity<>(attendances, HttpStatus.OK);
    }

    @PostMapping("/check-in")
    public ResponseEntity<UserAttendanceResponse> checkInAttendance(@AuthenticationPrincipal User user) {
       UserAttendanceResponse response = userAttendanceService.createAttendance(user, AttendanceType.PUNCH_IN);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/check-out")
    public ResponseEntity<UserAttendanceResponse> checkOutAttendance(@AuthenticationPrincipal User user) {
        UserAttendanceResponse response = userAttendanceService.createAttendance(user, AttendanceType.PUNCH_OUT);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
