package com.example.exammonitor.model;

import java.time.LocalDate;

public class StudentInfo {
    private String fullName;
    private String fullNameNoAccent;
    private String email;
    private String phone;
    private String image;
    private String address;
    private String major;
    private String classroom;
    private LocalDate birthday;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
        this.fullNameNoAccent = com.example.exammonitor.util.VietnameseAccentRemover.removeVietnameseAccent(fullName);
    }

    public String getFullNameNoAccent() {
        return fullNameNoAccent;
    }

    public void setFullNameNoAccent(String fullNameNoAccent) {
        this.fullNameNoAccent = fullNameNoAccent;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
