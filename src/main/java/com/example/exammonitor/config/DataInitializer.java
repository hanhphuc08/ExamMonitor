package com.example.exammonitor.config;

import com.example.exammonitor.model.User;
import com.example.exammonitor.model.UserRole;
import com.example.exammonitor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Kiểm tra xem đã có user nào chưa
        if (userRepository.count() == 0) {
            // Tạo user admin
            User admin = new User();
            admin.setId("admin");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFullName("Administrator");
            admin.setEmail("admin@example.com");
            admin.setRole(UserRole.ADMIN);
            admin.setCreatedAt(LocalDate.now());
            userRepository.save(admin);

            // Tạo user invigilator
            User invigilator = new User();
            invigilator.setId("gv");
            invigilator.setUsername("gv");
            invigilator.setPassword(passwordEncoder.encode("gv123"));
            invigilator.setFullName("Giảng viên");
            invigilator.setEmail("gv@example.com");
            invigilator.setRole(UserRole.INVIGILATOR);
            invigilator.setCreatedAt(LocalDate.now());
            userRepository.save(invigilator);

            System.out.println("Default users created successfully!");
        }
    }
} 