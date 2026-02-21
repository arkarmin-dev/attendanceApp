package com.akm.attendanceApp.features.adminUserManagement.dto.request;

public record UserRequest(
        String username,
        String password,
        String fullName,
        String email,
        String phoneNum
) {}
