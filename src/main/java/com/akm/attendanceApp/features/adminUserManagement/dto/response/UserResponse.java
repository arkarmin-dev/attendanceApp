package com.akm.attendanceApp.features.adminUserManagement.dto.response;

import com.akm.attendanceApp.data.enums.Role;
import lombok.Builder;

@Builder
public record UserResponse(
    long id,
    String fullName,
    String email,
    String phoneNum,
    Role role
) {}
