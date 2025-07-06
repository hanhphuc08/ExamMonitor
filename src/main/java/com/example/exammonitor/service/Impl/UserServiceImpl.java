package com.example.exammonitor.service.Impl;

import com.example.exammonitor.model.User;
import com.example.exammonitor.repository.UserRepository;
import com.example.exammonitor.service.StudentService;
import com.example.exammonitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public User saveUser(User user) {
        // Nếu là user mới (chưa có ID) hoặc password đã thay đổi
        if (user.getId() == null || user.getId().isEmpty()) {
            // User mới - mã hóa password
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setCreatedAt(LocalDate.now());
        } else {
            // User đã tồn tại - kiểm tra xem password có thay đổi không
            Optional<User> existingUser = userRepository.findById(user.getId());
            if (existingUser.isPresent()) {
                User existing = existingUser.get();
                // Nếu password không thay đổi, giữ nguyên password đã mã hóa
                if (!user.getPassword().equals(existing.getPassword()) && 
                    !passwordEncoder.matches(user.getPassword(), existing.getPassword())) {
                    // Password đã thay đổi - mã hóa password mới
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                } else {
                    // Password không thay đổi - giữ nguyên
                    user.setPassword(existing.getPassword());
                }
                user.setCreatedAt(existing.getCreatedAt());
            }
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }
}
