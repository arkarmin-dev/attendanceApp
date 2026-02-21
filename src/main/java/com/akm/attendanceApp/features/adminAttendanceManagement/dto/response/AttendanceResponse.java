package com.akm.attendanceApp.features.adminAttendanceManagement.dto.response;

import lombok.Builder;

@Builder
public record AttendanceResponse(
    String userFullName,
    String date,
    String inTime,
    String outTime
) {}
