package com.example.exammonitor.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Document("students")
public class Student {
    @Id
    private String id;
    private String studentId;
    private StudentInfo currentInfo;
    private List<StudentInfo> infoHistory;
    private List<ExamParticipation> examParticipations;
    private StudentStatus status;
    private List<ChangedInfo> changedInfos;
    private List<ParticipatedExam> participatedExams;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public StudentInfo getCurrentInfo() {
        return currentInfo;
    }

    public void setCurrentInfo(StudentInfo currentInfo) {
        this.currentInfo = currentInfo;
    }

    public List<StudentInfo> getInfoHistory() {
        return infoHistory;
    }

    public void setInfoHistory(List<StudentInfo> infoHistory) {
        this.infoHistory = infoHistory;
    }

    public List<ExamParticipation> getExamParticipations() {
        return examParticipations;
    }

    public void setExamParticipations(List<ExamParticipation> examParticipations) {
        this.examParticipations = examParticipations;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    public List<ChangedInfo> getChangedInfos() {
        return changedInfos;
    }

    public void setChangedInfos(List<ChangedInfo> changedInfos) {
        this.changedInfos = changedInfos;
    }

    public List<ParticipatedExam> getParticipatedExams() {
        return participatedExams;
    }

    public void setParticipatedExams(List<ParticipatedExam> participatedExams) {
        this.participatedExams = participatedExams;
    }
// Getters & Setters
}

