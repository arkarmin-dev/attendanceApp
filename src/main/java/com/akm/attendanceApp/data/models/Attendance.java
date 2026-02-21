package com.akm.attendanceApp.data.models;

import com.akm.attendanceApp.data.BaseEntity;
import com.akm.attendanceApp.data.enums.AttendanceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Attendance extends BaseEntity {

    @Column(name = "punch_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.time.LocalDateTime punchTime;

    @Column
    @Enumerated(EnumType.STRING)
    private AttendanceType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
