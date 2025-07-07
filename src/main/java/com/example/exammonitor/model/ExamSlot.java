package com.example.exammonitor.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ExamSlot {
    private String shift;
    private LocalDate examDate;
    private LocalTime startTime;
    private String examId;
    private List<String> studentId;
    private List<String> invigilatorUserIds;

<<<<<<< HEAD
=======
    public ExamSlot() {}

>>>>>>> QA_branch
    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public List<String> getStudentId() {
<<<<<<< HEAD
=======
        if (studentId == null) studentId = new java.util.ArrayList<>();
>>>>>>> QA_branch
        return studentId;
    }

    public void setStudentId(List<String> studentId) {
        this.studentId = studentId;
    }

    public List<String> getInvigilatorUserIds() {
<<<<<<< HEAD
=======
        if (invigilatorUserIds == null) invigilatorUserIds = new java.util.ArrayList<>();
>>>>>>> QA_branch
        return invigilatorUserIds;
    }

    public void setInvigilatorUserIds(List<String> invigilatorUserIds) {
        this.invigilatorUserIds = invigilatorUserIds;
    }
}
