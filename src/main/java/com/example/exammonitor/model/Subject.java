package com.example.exammonitor.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Subject {
    private String subjectId;
    private String subjectName;
    private LocalDate examDate;
    private String shift;
    private LocalTime startTime;
    private ExamArea examArea;
    private ViolationReport violation;

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public ExamArea getExamArea() {
        return examArea;
    }

    public void setExamArea(ExamArea examArea) {
        this.examArea = examArea;
    }

    public ViolationReport getViolation() {
        return violation;
    }

    public void setViolation(ViolationReport violation) {
        this.violation = violation;
    }
}
