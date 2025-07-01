package com.example.exammonitor.service;

import com.example.exammonitor.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(String id);
    User saveUser(User User);
    void deleteUserById(String id);
}
