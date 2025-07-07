package com.example.exammonitor.controller;

import com.example.exammonitor.model.ExamArea;
import com.example.exammonitor.service.ExamAreaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
class ExamAreaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    // Nếu muốn mock service, giữ lại đoạn này, nếu không thì xóa để test integration thật
    @MockBean
    private ExamAreaService examAreaService;

    private ExamArea area;

    @BeforeEach
    void setUp() {
        area = new ExamArea();
        area.setId("a1");
        area.setName("Khu A");
        // Không có setDescription
    }

    // --- Security Test ---
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testListExamAreasAllowedForAdmin() throws Exception {
        mockMvc.perform(get("/examareas"))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gv", roles = {"INVIGILATOR"})
    void testListExamAreasAllowedForInvigilator() throws Exception {
        mockMvc.perform(get("/examareas"))
               .andExpect(status().isOk());
    }

    @Test
    void testListExamAreasUnauthorizedForAnonymous() throws Exception {
        mockMvc.perform(get("/examareas"))
                .andExpect(status().isFound()) // 302
                .andExpect(redirectedUrlPattern("**/login"));
    }

    // --- Integration Test ---
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testListExamAreasIntegration() throws Exception {
        mockMvc.perform(get("/examareas"))
               .andExpect(status().isOk())
               .andExpect(view().name("examareas/list"))
               .andExpect(model().attributeExists("examareas"));
    }

    // --- Các test logic cũ (nếu muốn giữ lại mock service thì bỏ comment @MockBean ở trên) ---
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAddForm() throws Exception {
        mockMvc.perform(get("/examareas/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("examareas/form"))
                .andExpect(model().attributeExists("examarea"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowEditForm() throws Exception {
        when(examAreaService.getExamAreaById("a1")).thenReturn(Optional.of(area));
        mockMvc.perform(get("/examareas/edit/a1"))
                .andExpect(status().isOk())
                .andExpect(view().name("examareas/form"))
                .andExpect(model().attributeExists("examarea"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testSaveExamArea() throws Exception {
        when(examAreaService.saveExamArea(any(ExamArea.class))).thenReturn(area);
        mockMvc.perform(MockMvcRequestBuilders.post("/examareas/save")
                        .with(csrf())
                        .flashAttr("examarea", area))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/examareas"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteExamArea() throws Exception {
        doNothing().when(examAreaService).deleteExamAreaById("a1");
        mockMvc.perform(get("/examareas/delete/a1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/examareas"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testSearchById() throws Exception {
        when(examAreaService.searchById("A")).thenReturn(Arrays.asList(area));
        mockMvc.perform(get("/examareas")
                .param("searchField", "id")
                .param("keyword", "A"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("examareas"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("A")));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testSearchByNameNoAccent() throws Exception {
        when(examAreaService.searchByName("Khu A")).thenReturn(Arrays.asList(area));
        mockMvc.perform(get("/examareas")
                .param("searchField", "name")
                .param("keyword", "Khu A"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("examareas"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Khu A")));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testSearchByNameWithAccent() throws Exception {
        when(examAreaService.searchByName("Khu Á")).thenReturn(Arrays.asList(area));
        mockMvc.perform(get("/examareas")
                .param("searchField", "name")
                .param("keyword", "Khu Á"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("examareas"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Khu A")));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testSearchByIdWithName() throws Exception {
        when(examAreaService.searchById("Khu A")).thenReturn(Arrays.asList());
        mockMvc.perform(get("/examareas")
                .param("searchField", "id")
                .param("keyword", "Khu A"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("examareas"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Không có khu vực thi nào khớp với nội dung tìm kiếm")));
    }
} 