package com.example.exammonitor.repository;

import com.example.exammonitor.model.ExamArea;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExamAreaRepository extends MongoRepository<ExamArea, String> {
    Optional<ExamArea> findByName(String name);

    @Query("{ $or: [ { 'id': { $regex: ?0, $options: 'i' } }, { 'name': { $regex: ?0, $options: 'i' } } ] }")
    List<ExamArea> searchByIdOrName(String keyword);

    @Query("{ 'id': { $regex: ?0, $options: 'i' } }")
    List<ExamArea> findByIdRegex(String id);

    @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
    List<ExamArea> findByNameRegex(String name);
}
