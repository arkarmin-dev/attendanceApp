package com.akm.attendanceApp.data.repositories;

import com.akm.attendanceApp.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findUserDetailsByUsername(String username);

    User findByUsername(String username);
}
