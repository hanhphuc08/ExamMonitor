package com.example.exammonitor.repository;

import com.example.exammonitor.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository  extends MongoRepository<User, String> {
}

