package com.example.exammonitor.model;

import java.util.List;

public class ViolationReport {
    private boolean hasViolation;
    private ViolationLevel violationLevel;
    private String description;
    private List<String> invigilatorUserIds;
    private String notes;

    public boolean isHasViolation() {
        return hasViolation;
    }

    public void setHasViolation(boolean hasViolation) {
        this.hasViolation = hasViolation;
    }

    public ViolationLevel getViolationLevel() {
        return violationLevel;
    }

    public void setViolationLevel(ViolationLevel violationLevel) {
        this.violationLevel = violationLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getInvigilatorUserIds() {
        return invigilatorUserIds;
    }

    public void setInvigilatorUserIds(List<String> invigilatorUserIds) {
        this.invigilatorUserIds = invigilatorUserIds;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
