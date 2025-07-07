package com.example.exammonitor.service;

import com.example.exammonitor.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface StudentService {
    List<Student> getAllStudents();
    Optional<Student> getStudentById(String id);
    Student saveStudent(Student student);
    void deleteStudentById(String id);
    List<Student> searchStudents(String keyword);
    List<Student> searchByStudentId(String studentId);
    List<Student> searchByFullName(String fullName);
}
