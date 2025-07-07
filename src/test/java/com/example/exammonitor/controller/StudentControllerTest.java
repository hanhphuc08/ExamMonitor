package com.example.exammonitor.controller;

import com.example.exammonitor.model.Student;
import com.example.exammonitor.model.StudentInfo;
import com.example.exammonitor.model.StudentStatus;
import com.example.exammonitor.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        // Tạo dữ liệu test trong database
        student = new Student();
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
        
        // Lưu student vào database để test có thể tìm thấy
        try {
            studentService.saveStudent(student);
        } catch (Exception e) {
            // Nếu student đã tồn tại thì bỏ qua
        }
    }

    // Unit tests
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testListStudentsAllowedForAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students"))
                .andExpect(status().isOk());
    }



    @Test
    void testListStudentsUnauthorizedForAnonymous() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students"))
                .andExpect(status().isFound()) // 302
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAddForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/form"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowEditForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students/edit/s1"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/form"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testSaveStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/students/save")
                        .with(csrf())
                        .flashAttr("student", student))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students/delete/s1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"));
    }

    // Integration tests
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testListStudentsIntegration() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students"))
               .andExpect(status().isOk())
               .andExpect(view().name("students/list"))
               .andExpect(model().attributeExists("students"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testSearchByStudentId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students")
                .param("searchField", "studentId")
                .param("keyword", "s1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("students"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("s1")));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testSearchByFullNameNoAccent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students")
                .param("searchField", "fullName")
                .param("keyword", "Nguyen Van A"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("students"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Nguyen Van A")));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testSearchByFullNameWithAccent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students")
                .param("searchField", "fullName")
                .param("keyword", "Nguyễn Văn A"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("students"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Nguyen Van A")));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testSearchByStudentIdWithName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students")
                .param("searchField", "studentId")
                .param("keyword", "Nguyen Van A"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("students"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Nguyen Van A")));
    }
} 