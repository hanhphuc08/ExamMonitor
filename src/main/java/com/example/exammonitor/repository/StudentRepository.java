package com.example.exammonitor.repository;

import com.example.exammonitor.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student, String> {
    Optional<Student> findByStudentId(String studentId);

    @Query("{" +
            " $or: [" +
            "   { 'studentId': { $regex: ?0, $options: 'i' } }," +
            "   { 'currentInfo.fullName': { $regex: ?0, $options: 'i' } }," +
            "   { 'currentInfo.fullNameNoAccent': { $regex: ?0, $options: 'i' } }" +
            " ]" +
            "}")
    List<Student> searchByStudentIdOrFullNameOrNoAccent(String keyword);
}

