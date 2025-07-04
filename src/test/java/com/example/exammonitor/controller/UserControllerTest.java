package com.example.exammonitor.controller;

import com.example.exammonitor.model.User;
import com.example.exammonitor.model.UserRole;
import com.example.exammonitor.service.UserService;
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
class   UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        // Tạo dữ liệu test trong database
        user = new User();
        user.setId("u1");
        user.setUsername("admin");
        user.setPassword("admin123");
        user.setFullName("Nguyen Van Admin");
        user.setEmail("admin@example.com");
        user.setRole(UserRole.ADMIN);
        user.setCreatedAt(LocalDate.of(2024, 1, 1));
        
        // Lưu user vào database để test có thể tìm thấy
        try {
            userService.saveUser(user);
        } catch (Exception e) {
            // Nếu user đã tồn tại thì bỏ qua
        }
    }

    // Unit tests (với mock service)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testListUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/list"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAddForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/form"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowEditForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/edit/u1"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/form"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testSaveUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/save")
                        .with(csrf())
                        .flashAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/delete/u1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    // Security tests
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testListUsersAllowedForAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gv", roles = {"INVIGILATOR"})
    void testListUsersForbiddenForInvigilator() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
               .andExpect(status().isForbidden());
    }


    // Integration tests
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testListUsersIntegration() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
               .andExpect(status().isOk())
               .andExpect(view().name("users/list"))
               .andExpect(model().attributeExists("users"));
    }
} 