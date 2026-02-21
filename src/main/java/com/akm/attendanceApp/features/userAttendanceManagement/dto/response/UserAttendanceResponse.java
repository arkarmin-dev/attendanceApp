package com.akm.attendanceApp.features.userAttendanceManagement.dto.response;

import lombok.Builder;

@Builder
public record UserAttendanceResponse(
        String date,
        String inTime,
        String outTime
) {
}
