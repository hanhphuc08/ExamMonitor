package com.example.exammonitor.model;

import java.time.LocalDate;

public class ChangedInfo {
    private LocalDate changedDate;
    private PreviousInfo previousInfo;

    public LocalDate getChangedDate() {
        return changedDate;
    }

    public void setChangedDate(LocalDate changedDate) {
        this.changedDate = changedDate;
    }

    public PreviousInfo getPreviousInfo() {
        return previousInfo;
    }

    public void setPreviousInfo(PreviousInfo previousInfo) {
        this.previousInfo = previousInfo;
    }
}
