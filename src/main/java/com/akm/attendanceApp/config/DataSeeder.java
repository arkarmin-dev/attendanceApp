package com.akm.attendanceApp.config;

import com.akm.attendanceApp.data.enums.Role;
import com.akm.attendanceApp.data.models.User;
import com.akm.attendanceApp.data.models.UserDetail;
import com.akm.attendanceApp.data.repositories.UserDetailRepository;
import com.akm.attendanceApp.data.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    @Transactional
    CommandLineRunner initDatabase(UserRepository userRepo,UserDetailRepository detailRepo) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return args -> {
            // Check if seeding is needed
            if (userRepo.count() == 0) {

                createUser(userRepo, detailRepo, encoder,
                        "thithioo", "thithioo@trustlinkmm.com", "Thi Thi Oo", Role.USER);

                createUser(userRepo, detailRepo, encoder,
                        "admin_admin", "admin@trustlink.com", "System Admin", Role.ADMIN);

                createUser(userRepo, detailRepo, encoder, "mchen", "m.chen@trustlink.com", "Michael Chen", Role.USER);
                createUser(userRepo, detailRepo, encoder, "sjenkins", "s.jenkins@trustlink.com", "Sarah Jenkins", Role.USER);
                createUser(userRepo, detailRepo, encoder, "dmiller", "d.miller@trustlink.com", "David Miller", Role.ADMIN);
                createUser(userRepo, detailRepo, encoder, "ewilson", "e.wilson@trustlink.com", "Emily Wilson", Role.USER);
                createUser(userRepo, detailRepo, encoder, "janderson", "j.anderson@trustlink.com", "James Anderson", Role.USER);
                createUser(userRepo, detailRepo, encoder, "lmartinez", "l.martinez@trustlink.com", "Linda Martinez", Role.USER);
                createUser(userRepo, detailRepo, encoder, "rtaylor", "r.taylor@trustlink.com", "Robert Taylor", Role.USER);
                createUser(userRepo, detailRepo, encoder, "jthomas", "j.thomas@trustlink.com", "Jennifer Thomas", Role.USER);
                createUser(userRepo, detailRepo, encoder, "wwhite", "w.white@trustlink.com", "William White", Role.USER);
                createUser(userRepo, detailRepo, encoder, "kharris", "k.harris@trustlink.com", "Karen Harris", Role.USER);

                System.out.println("Data seeding completed successfully.");
            }
        };
    }

    private void createUser(UserRepository uRepo, UserDetailRepository dRepo,
                            BCryptPasswordEncoder encoder, String username,
                            String email, String fullName, Role role) {

        // CREATE USER ENTITY
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode("password")); // DEFAULT
        user.setRole(role);
        User savedUser = uRepo.save(user);

        // CREATE USER DETAIL ENTITY
        UserDetail detail = new UserDetail();
        detail.setFullName(fullName);
        detail.setEmail(email);
        detail.setPhoneNum("912345678");
        detail.setUser(savedUser);
        dRepo.save(detail);
    }
}
