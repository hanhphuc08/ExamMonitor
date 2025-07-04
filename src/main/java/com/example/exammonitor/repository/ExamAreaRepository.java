package com.example.exammonitor.repository;

import com.example.exammonitor.model.ExamArea;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ExamAreaRepository extends MongoRepository<ExamArea, String> {
    Optional<ExamArea> findByName(String name);
}
