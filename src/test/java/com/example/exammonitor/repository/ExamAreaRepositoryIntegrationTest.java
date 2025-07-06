package com.example.exammonitor.repository;

import com.example.exammonitor.model.ExamArea;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class ExamAreaRepositoryIntegrationTest {
    @Autowired
    private ExamAreaRepository examAreaRepository;

    @AfterEach
    void tearDown() {
        examAreaRepository.deleteAll();
    }

    @Test
    void testInsertAndFindExamArea() {
        ExamArea area = new ExamArea();
        area.setId("a1");
        area.setName("Khu A");

        examAreaRepository.save(area);

        Optional<ExamArea> found = examAreaRepository.findById("a1");
        assertTrue(found.isPresent());
        assertEquals("Khu A", found.get().getName());
    }

    @Test
    void testFindByName() {
        ExamArea area = new ExamArea();
        area.setId("a2");
        area.setName("Khu B");
        area.setDescription("Khu thi B");

        examAreaRepository.save(area);

        Optional<ExamArea> found = examAreaRepository.findByName("Khu B");
        assertTrue(found.isPresent());
        assertEquals("Khu B", found.get().getName());
        assertEquals("Khu thi B", found.get().getDescription());
    }

    @Test
    void testDeleteExamArea() {
        ExamArea area = new ExamArea();
        area.setId("a3");
        area.setName("Khu Test");
        area.setDescription("Khu thi test");

        examAreaRepository.save(area);
        assertTrue(examAreaRepository.findById("a3").isPresent());

        examAreaRepository.deleteById("a3");
        assertFalse(examAreaRepository.findById("a3").isPresent());
    }
} 