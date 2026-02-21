package com.akm.attendanceApp.data.models;

import com.akm.attendanceApp.data.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "leaves")
@NoArgsConstructor
public class Leave extends BaseEntity {

    @Column(name = "is_approved")
    private boolean isApproved = false;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
}
