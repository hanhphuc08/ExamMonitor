package com.example.exammonitor.service.Impl;

import com.example.exammonitor.model.ExamArea;
import com.example.exammonitor.repository.ExamAreaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExamAreaServiceImplTest {
    @Mock
    private ExamAreaRepository examAreaRepository;

    @InjectMocks
    private ExamAreaServiceImpl examAreaService;

    private ExamArea area1;
    private ExamArea area2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        area1 = new ExamArea();
        area1.setId("a1");
        area1.setName("Khu A");
        //area1.setDescription("Khu vực thi số 1");

        area2 = new ExamArea();
        area2.setId("a2");
        area2.setName("Khu B");
        //area2.setDescription("Khu vực thi số 2");
    }

    @Test
    void testGetAllExamAreas() {
        when(examAreaRepository.findAll()).thenReturn(Arrays.asList(area1, area2));
        List<ExamArea> areas = examAreaService.getAllExamAreas();
        assertEquals(2, areas.size());
        assertEquals("a1", areas.get(0).getId());
        assertEquals("a2", areas.get(1).getId());
    }

    @Test
    void testGetExamAreaById() {
        when(examAreaRepository.findById("a1")).thenReturn(Optional.of(area1));
        Optional<ExamArea> found = examAreaService.getExamAreaById("a1");
        assertTrue(found.isPresent());
        assertEquals("Khu A", found.get().getName());
    }

    @Test
    void testSaveExamArea() {
        when(examAreaRepository.save(area1)).thenReturn(area1);
        ExamArea saved = examAreaService.saveExamArea(area1);
        assertNotNull(saved);
        assertEquals("a1", saved.getId());
    }

    @Test
    void testDeleteExamAreaById() {
        doNothing().when(examAreaRepository).deleteById("a1");
        examAreaService.deleteExamAreaById("a1");
        verify(examAreaRepository, times(1)).deleteById("a1");
    }

    @Test
    void testUpdateExamArea() {
        area1.setName("Khu A Updated");
        when(examAreaRepository.save(area1)).thenReturn(area1);
        ExamArea updated = examAreaService.saveExamArea(area1);
        assertEquals("Khu A Updated", updated.getName());
    }

    @Test
    void testSearchById() {
        when(examAreaRepository.findByIdRegex("a1")).thenReturn(Arrays.asList(area1));
        List<ExamArea> result = examAreaService.searchById("a1");
        assertEquals(1, result.size());
        assertEquals("a1", result.get(0).getId());
    }

    @Test
    void testSearchByName() {
        when(examAreaRepository.findByNameRegex("Khu A")).thenReturn(Arrays.asList(area1));
        List<ExamArea> result = examAreaService.searchByName("Khu A");
        assertEquals(1, result.size());
        assertEquals("Khu A", result.get(0).getName());
    }

    @Test
    void testSearchByIdWithName() {
        when(examAreaRepository.findByIdRegex("Khu A")).thenReturn(Arrays.asList());
        List<ExamArea> result = examAreaService.searchById("Khu A");
        assertTrue(result.isEmpty());
    }

    @Test
    void testSearchByNameWithId() {
        when(examAreaRepository.findByNameRegex("a1")).thenReturn(Arrays.asList());
        List<ExamArea> result = examAreaService.searchByName("a1");
        assertTrue(result.isEmpty());
    }
} 