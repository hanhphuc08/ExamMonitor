package com.example.exammonitor.repository;

import com.example.exammonitor.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, String> {
}

