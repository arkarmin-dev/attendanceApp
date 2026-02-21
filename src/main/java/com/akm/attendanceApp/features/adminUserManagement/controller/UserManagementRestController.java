package com.akm.attendanceApp.features.adminUserManagement.controller;

import com.akm.attendanceApp.features.adminUserManagement.dto.request.PatchUserDetailRequest;
import com.akm.attendanceApp.features.adminUserManagement.dto.request.UserRequest;
import com.akm.attendanceApp.features.adminUserManagement.dto.response.UserResponse;
import com.akm.attendanceApp.features.adminUserManagement.service.adminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/user")
@RequiredArgsConstructor
public class UserManagementRestController {

    @Autowired
    private final adminUserService service;

    @GetMapping("/list")
    private ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = service.getAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    private ResponseEntity<UserResponse> getUserById(@PathVariable("id") long id) {
        UserResponse user = service.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/create")
    private ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        return new ResponseEntity<>(service.createUser(request), HttpStatus.CREATED);
    }

    @PatchMapping("/update/{id}")
    private ResponseEntity<UserResponse> updateUser(@RequestBody PatchUserDetailRequest request, @PathVariable("id") long id) {
        return new ResponseEntity<>(service.updateUser(request, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private void deleteUser(@PathVariable("id") long id) {
        service.deleteUserById(id);
    }

}
