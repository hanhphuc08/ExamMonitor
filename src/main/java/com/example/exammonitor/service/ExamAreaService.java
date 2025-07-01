package com.example.exammonitor.service;

import com.example.exammonitor.model.ExamArea;

import java.util.List;
import java.util.Optional;

public interface ExamAreaService {
    List<ExamArea> getAllExamAreas();
    Optional<ExamArea> getExamAreaById(String id);
    ExamArea saveExamArea(ExamArea ExamArea);
    void deleteExamAreaById(String id);
}
