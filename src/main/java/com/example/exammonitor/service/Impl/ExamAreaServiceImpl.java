package com.example.exammonitor.service.Impl;

import com.example.exammonitor.model.ExamArea;
import com.example.exammonitor.repository.ExamAreaRepository;
import com.example.exammonitor.service.ExamAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamAreaServiceImpl implements ExamAreaService {
    @Autowired
    private ExamAreaRepository examAreaRepository;
    @Override
    public List<ExamArea> getAllExamAreas() {
        return examAreaRepository.findAll();
    }

    @Override
    public Optional<ExamArea> getExamAreaById(String id) {
        return examAreaRepository.findById(id);
    }

    @Override
    public ExamArea saveExamArea(ExamArea ExamArea) {
        return examAreaRepository.save(ExamArea);
    }

    @Override
    public void deleteExamAreaById(String id) {
        examAreaRepository.deleteById(id);
    }
}
