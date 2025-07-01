package com.example.exammonitor.service.Impl;

import com.example.exammonitor.model.User;
import com.example.exammonitor.repository.UserRepository;
import com.example.exammonitor.service.StudentService;
import com.example.exammonitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public User saveUser(User User) {
        return userRepository.save(User);
    }

    @Override
    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }
}
