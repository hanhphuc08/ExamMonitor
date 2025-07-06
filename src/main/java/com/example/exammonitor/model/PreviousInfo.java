package com.example.exammonitor.model;

public class PreviousInfo {
    private String studentId;
    private StudentInfo currentInfo;

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
}
