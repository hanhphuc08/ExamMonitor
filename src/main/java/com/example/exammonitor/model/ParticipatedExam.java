package com.example.exammonitor.model;

import java.time.LocalDate;
import java.util.List;

public class ParticipatedExam {
    private String examId;
    private String examName;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<SubjectResult> subjectResults;

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<SubjectResult> getSubjectResults() {
        return subjectResults;
    }

    public void setSubjectResults(List<SubjectResult> subjectResults) {
        this.subjectResults = subjectResults;
    }
}
