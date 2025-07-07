package com.example.exammonitor.service.Impl;

import com.example.exammonitor.model.Student;
import com.example.exammonitor.model.StudentInfo;
import com.example.exammonitor.model.StudentStatus;
import com.example.exammonitor.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        student1 = new Student();
        student1.setStudentId("s1");
        StudentInfo info1 = new StudentInfo();
        info1.setFullName("Nguyen Van A");
        info1.setEmail("a@student.edu");
        info1.setPhone("0123456789");
        info1.setMajor("CNTT");
        info1.setClassroom("21CLC");
        info1.setBirthday(LocalDate.of(2000,1,1));
        student1.setCurrentInfo(info1);
        student1.setStatus(StudentStatus.active);

        student2 = new Student();
        student2.setStudentId("s2");
        StudentInfo info2 = new StudentInfo();
        info2.setFullName("Tran Thi B");
        info2.setEmail("b@student.edu");
        info2.setPhone("0987654321");
        info2.setMajor("KTPM");
        info2.setClassroom("21KTPM");
        info2.setBirthday(LocalDate.of(2001,2,2));
        student2.setCurrentInfo(info2);
        student2.setStatus(StudentStatus.suspended);
    }

    @Test
    void testGetAllStudents() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));
        List<Student> students = studentService.getAllStudents();
        assertEquals(2, students.size());
        assertEquals("s1", students.get(0).getStudentId());
        assertEquals("s2", students.get(1).getStudentId());
    }

    @Test
    void testGetStudentById() {
        when(studentRepository.findById("s1")).thenReturn(Optional.of(student1));
        Optional<Student> found = studentService.getStudentById("s1");
        assertTrue(found.isPresent());
        assertEquals("Nguyen Van A", found.get().getCurrentInfo().getFullName());
    }

    @Test
    void testSaveStudent() {
        when(studentRepository.save(student1)).thenReturn(student1);
        Student saved = studentService.saveStudent(student1);
        assertNotNull(saved);
        assertEquals("s1", saved.getStudentId());
    }

    @Test
    void testDeleteStudentById() {
        doNothing().when(studentRepository).deleteById("s1");
        studentService.deleteStudentById("s1");
        verify(studentRepository, times(1)).deleteById("s1");
    }

    @Test
    void testUpdateStudent() {
        // Giả sử cập nhật là gọi save với student đã tồn tại
        student1.getCurrentInfo().setFullName("Nguyen Van A Updated");
        when(studentRepository.save(student1)).thenReturn(student1);
        Student updated = studentService.saveStudent(student1);
        assertEquals("Nguyen Van A Updated", updated.getCurrentInfo().getFullName());
    }

    @Test
    void testSearchByStudentId() {
        when(studentRepository.findByStudentIdRegex("s1")).thenReturn(Arrays.asList(student1));
        List<Student> result = studentService.searchByStudentId("s1");
        assertEquals(1, result.size());
        assertEquals("s1", result.get(0).getStudentId());
    }



    @Test
    void testSearchByStudentIdWithName() {
        when(studentRepository.findByStudentIdRegex("Nguyen Van A")).thenReturn(Arrays.asList());
        List<Student> result = studentService.searchByStudentId("Nguyen Van A");
        assertTrue(result.isEmpty());
    }

    @Test
    void testSearchByFullNameWithStudentId() {
        when(studentRepository.findByFullNameRegex("s1")).thenReturn(Arrays.asList());
        List<Student> result = studentService.searchByFullName("s1");
        assertTrue(result.isEmpty());
    }
} 