package com.akm.attendanceApp.features.adminUserManagement.dto.request;

import lombok.Data;

@Data
public class PatchUserDetailRequest {

    private String fullName;
    private String email;
    private String phoneNum;

}
