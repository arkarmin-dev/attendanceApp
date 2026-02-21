package com.akm.attendanceApp.data.repositories;

import com.akm.attendanceApp.data.models.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    Optional<Object> findByUser_Id(Long userId);
    UserDetail findByUser_Username(String userUsername);
}
