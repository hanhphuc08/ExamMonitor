package com.example.exammonitor.repository;

import com.example.exammonitor.model.ExamArea;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExamAreaRepository extends MongoRepository<ExamArea, String> {
}
