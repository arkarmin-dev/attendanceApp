package com.akm.attendanceApp.features.adminUserManagement.service;

import com.akm.attendanceApp.data.enums.Role;
import com.akm.attendanceApp.data.models.User;
import com.akm.attendanceApp.data.models.UserDetail;
import com.akm.attendanceApp.data.repositories.UserDetailRepository;
import com.akm.attendanceApp.data.repositories.UserRepository;
import com.akm.attendanceApp.features.adminUserManagement.dto.request.PatchUserDetailRequest;
import com.akm.attendanceApp.features.adminUserManagement.dto.request.UserRequest;
import com.akm.attendanceApp.features.adminUserManagement.dto.response.UserResponse;
import com.akm.attendanceApp.utils.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class adminUserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserDetailRepository userDetailRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        if(users.isEmpty()) {
            return Collections.emptyList();
        }

        return users.stream().filter(user -> user.getRole() == Role.USER)
                .map(user -> buildUserResponse(user.getId())).collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User with ID " + id + " not found")
        );

        return buildUserResponse(user.getId());
    }

    @Transactional
    public UserResponse createUser(UserRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(encoder.encode(request.password()));
        user.setRole(Role.USER);
        userRepository.save(user);

        UserDetail userDetail = new UserDetail();
        userDetail.setUser(user);
        userDetail.setFullName(request.fullName());
        userDetail.setEmail(request.email());
        userDetail.setPhoneNum(request.phoneNum());
        userDetailRepository.save(userDetail);

        return buildUserResponse(user.getId());
    }

    public UserResponse updateUser(PatchUserDetailRequest request, Long id) {
        UserDetail userDetail = (UserDetail) userDetailRepository.findByUser_Id(id).orElseThrow(
                () -> new ResourceNotFoundException("User Detail with user id: " + id + " not found.")
        );

        UserResponse userResponse = UserResponse.builder()
                .id(id)
                .fullName(request.getFullName() == null ? userDetail.getFullName() : request.getFullName())
                .email(request.getEmail() == null ? userDetail.getEmail() : request.getEmail())
                .phoneNum(request.getPhoneNum() == null ? userDetail.getPhoneNum() : request.getPhoneNum())
                .build();

        userDetail.setFullName(userResponse.fullName());
        userDetail.setEmail(userResponse.email());
        userDetail.setPhoneNum(userResponse.phoneNum());
        userDetailRepository.save(userDetail);

        return userResponse;
    }

    public void deleteUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User Detail with user id: " + id + " not found.")
        );

        userRepository.deleteById(id);
    }

    private UserResponse buildUserResponse(long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User Detail with user id: " + userId + " not found.")
        );

        UserDetail userDetail = (UserDetail) userDetailRepository.findByUser_Id(userId).orElse(null);

        return UserResponse.builder()
                .id(userId)
                .fullName(userDetail.getFullName())
                .email(userDetail.getEmail())
                .phoneNum(userDetail.getPhoneNum())
                .role(user.getRole())
                .build();
    }
}
