package com.example.exammonitor.repository;

import com.example.exammonitor.model.Student;
import com.example.exammonitor.model.StudentInfo;
import com.example.exammonitor.model.StudentStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class StudentRepositoryIntegrationTest {

    @Autowired
    private StudentRepository studentRepository;

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void testInsertAndFindStudent() {
        Student student = new Student();
        student.setStudentId("s1");
        StudentInfo info = new StudentInfo();
        info.setFullName("Nguyen Van A");
        info.setEmail("a@student.edu");
        info.setPhone("0123456789");
        info.setMajor("CNTT");
        info.setClassroom("21CLC");
        info.setBirthday(LocalDate.of(2000,1,1));
        student.setCurrentInfo(info);
        student.setStatus(StudentStatus.active);

        studentRepository.save(student);

        Optional<Student> found = studentRepository.findById("s1");
        assertTrue(found.isPresent());
        assertEquals("Nguyen Van A", found.get().getCurrentInfo().getFullName());
    }

    @Test
    void testFindByStudentId() {
        Student student = new Student();
        student.setStudentId("S001");
        StudentInfo info = new StudentInfo();
        info.setFullName("Tran Thi B");
        info.setEmail("b@student.edu");
        info.setPhone("0987654321");
        info.setMajor("CNTT");
        info.setClassroom("22CLC");
        info.setBirthday(LocalDate.of(2001,2,2));
        student.setCurrentInfo(info);
        student.setStatus(StudentStatus.active);

        studentRepository.save(student);

        Optional<Student> found = studentRepository.findByStudentId("S001");
        assertTrue(found.isPresent());
        assertEquals("Tran Thi B", found.get().getCurrentInfo().getFullName());
        assertEquals("S001", found.get().getStudentId());
    }

    @Test
    void testDeleteStudent() {
        Student student = new Student();
        student.setStudentId("S002");
        StudentInfo info = new StudentInfo();
        info.setFullName("Le Van C");
        info.setEmail("c@student.edu");
        info.setPhone("0555666777");
        info.setMajor("CNTT");
        info.setClassroom("23CLC");
        info.setBirthday(LocalDate.of(2002,3,3));
        student.setCurrentInfo(info);
        student.setStatus(StudentStatus.active);

        studentRepository.save(student);
        assertTrue(studentRepository.findById("S002").isPresent());

        studentRepository.deleteById("S002");
        assertFalse(studentRepository.findById("S002").isPresent());
    }
} 