package com.akm.attendanceApp.utils.helper;

import com.akm.attendanceApp.data.enums.AttendanceType;
import com.akm.attendanceApp.data.models.Attendance;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeResponseFormatter {

    public static String formatTimeResponse(Attendance attendance, AttendanceType attendanceType) {
        if(attendance.getType() != attendanceType) return "-";
        return attendance.getPunchTime().toString()
                .replaceAll(".*T","")
                .replaceAll("\\..*","");
    }

    public static String formatTimeToAmPm(LocalTime time) {
//        return time.toString().replaceAll("\\.\\d+$","");
        if(time == null) return "-";

        return time.format(DateTimeFormatter.ofPattern("hh:mm a"));
    }

    public static String formatDateResponse(java.time.LocalDateTime dateTime) {
        if(dateTime == null) return "-";
        return dateTime.toString().replaceAll("T.*","");
    }
}
