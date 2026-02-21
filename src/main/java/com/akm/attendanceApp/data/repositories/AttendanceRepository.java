package com.akm.attendanceApp.data.repositories;

import com.akm.attendanceApp.data.enums.AttendanceType;
import com.akm.attendanceApp.data.models.Attendance;
import com.akm.attendanceApp.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByUser(User user);

    @Query(value = """
    SELECT
        ud.full_name AS userFullName,
        Date(a.punch_time) as date,
        TIME(MIN(CASE WHEN a.type='PUNCH_IN' THEN a.punch_time END)) as inTime,
        TIME(MAX(CASE WHEN a.type='PUNCH_OUT' THEN a.punch_time END)) as outTime
    FROM attendance a
    JOIN user u ON a.user_id = u.id
    JOIN user_detail ud ON u.id = ud.user_id
    GROUP BY u.id, DATE(a.punch_time)
    ORDER BY date DESC
""", nativeQuery = true)
    List<Object[]> listAllAttendanceByUserDate();

    @Query("""
        SELECT COUNT(a) > 0 
        FROM Attendance a 
        WHERE a.user.id = :userId 
        AND CAST(a.punchTime AS localdate) = :today 
        AND a.type = :type
    """)
    boolean existsByUserIdAndDateAndType(
            @Param("userId") Long userId,
            @Param("today") java.time.LocalDate today,
            @Param("type") AttendanceType type
    );

    @Query(value = """
        SELECT
        TIME(punch_time)
        FROM attendance
        WHERE user_id = :userId
        AND DATE(punch_time) = :today
        AND type = :type
    """, nativeQuery = true)
    Time findTodayAttendance(
            @Param("userId") Long userId,
            @Param("today") java.time.LocalDate today,
            @Param("type") String type
    );
}