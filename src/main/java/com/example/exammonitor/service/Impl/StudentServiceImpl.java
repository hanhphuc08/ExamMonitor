package com.example.exammonitor.service.Impl;

import com.example.exammonitor.model.Student;
import com.example.exammonitor.repository.StudentRepository;
import com.example.exammonitor.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getStudentById(String id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student saveStudent(Student student) {

        if (student.getCurrentInfo() != null && student.getCurrentInfo().getFullName() != null) {
            String noAccent = com.example.exammonitor.util.VietnameseAccentRemover.removeVietnameseAccent(student.getCurrentInfo().getFullName());
            student.getCurrentInfo().setFullNameNoAccent(noAccent);
        }
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudentById(String id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> searchStudents(String keyword) {
        return studentRepository.searchByStudentIdOrFullNameOrNoAccent(com.example.exammonitor.util.VietnameseAccentRemover.removeVietnameseAccent(keyword));
    }
}
