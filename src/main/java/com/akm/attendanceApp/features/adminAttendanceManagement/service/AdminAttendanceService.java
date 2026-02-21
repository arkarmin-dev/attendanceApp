package com.akm.attendanceApp.features.adminAttendanceManagement.service;


import com.akm.attendanceApp.data.repositories.AttendanceRepository;
import com.akm.attendanceApp.data.repositories.UserDetailRepository;
import com.akm.attendanceApp.features.adminAttendanceManagement.dto.response.AttendanceResponse;
import com.akm.attendanceApp.utils.helper.DateTimeResponseFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminAttendanceService {

    @Autowired
    private final AttendanceRepository attendanceRepository;

    @Autowired
    private final UserDetailRepository userDetailRepository;

    public List<AttendanceResponse> getAllUsersAttendances() {
        List<Object[]> results = attendanceRepository.listAllAttendanceByUserDate();

        if (results.isEmpty()) return Collections.emptyList();
        return results.stream()
                .map(result -> AttendanceResponse.builder()
                        .userFullName((String) result[0])
                        .date(result[1].toString())
                        .inTime(result[2] != null ?
                                DateTimeResponseFormatter.formatTimeToAmPm((LocalTime) result[2]) : "-")
                        .outTime(result[3] !=null ?
                                DateTimeResponseFormatter.formatTimeToAmPm((LocalTime) result[3]) : "-")
                        .build())
                .toList();
    }
}
