package com.akm.attendanceApp.features.userAttendanceManagement.service;

import com.akm.attendanceApp.data.enums.AttendanceType;
import com.akm.attendanceApp.data.models.Attendance;
import com.akm.attendanceApp.data.models.User;
import com.akm.attendanceApp.data.repositories.AttendanceRepository;
import com.akm.attendanceApp.data.repositories.UserRepository;
import com.akm.attendanceApp.features.userAttendanceManagement.dto.response.UserAttendanceResponse;
import com.akm.attendanceApp.utils.helper.DateTimeResponseFormatter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final UserRepository userRepository;

    public List<UserAttendanceResponse> getAllAttendances(User user) {
        List<Attendance> attendances = attendanceRepository.findByUser(user);
        List<UserAttendanceResponse> responses = new ArrayList<>();

        if(attendances.isEmpty()) return Collections.emptyList();

        for(Attendance attendance : attendances) {
            responses.add(
                    UserAttendanceResponse.builder()
                            .date(DateTimeResponseFormatter.formatDateResponse(attendance.getPunchTime()))
                            .inTime(DateTimeResponseFormatter.formatTimeResponse(attendance,AttendanceType.PUNCH_IN))
                            .outTime(DateTimeResponseFormatter.formatTimeResponse(attendance, AttendanceType.PUNCH_OUT))
                            .build()
            );
        }
        return responses;
    }

    @Transactional
    public UserAttendanceResponse createAttendance(User user, AttendanceType type) {

        if(hasAttendanceToday(user.getId(), LocalDate.now(), type)) {
            throw new IllegalStateException("Attendance already exists");
        }

        Attendance attendance = new Attendance();
            attendance.setUser(user);
            attendance.setType(type);
            attendance.setPunchTime(java.time.LocalDateTime.now());

        attendanceRepository.save(attendance);

        return UserAttendanceResponse.builder()
                .date(DateTimeResponseFormatter.formatDateResponse(attendance.getPunchTime()))
                .inTime(DateTimeResponseFormatter.formatTimeResponse(attendance, AttendanceType.PUNCH_IN))
                .outTime(DateTimeResponseFormatter.formatTimeResponse(attendance, AttendanceType.PUNCH_OUT))
                .build();
    }

    public Long getIdByUsername(String username) {

        return userRepository.findByUsername(username) != null
                ? userRepository.findByUsername(username).getId()
                : null;
    }

    public boolean hasAttendanceToday(Long userId, LocalDate today, AttendanceType attendanceType) {
        return attendanceRepository.existsByUserIdAndDateAndType(
                userId,
                today,
                attendanceType);
    }

    public String getPunchLocalTimeToday(Long userId, AttendanceType attendanceType) {
        LocalDate today = LocalDate.now();
        Time time = attendanceRepository.findTodayAttendance(userId, today, attendanceType.name());

        if(time == null) return "";

        return DateTimeResponseFormatter.formatTimeToAmPm(time.toLocalTime());
    }
}
