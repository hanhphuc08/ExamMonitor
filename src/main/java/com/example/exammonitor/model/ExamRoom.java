package com.example.exammonitor.model;

import java.util.List;

public class ExamRoom {
    private String roomId;
    private int capacity;
    private List<ExamSlot> slots;

    public ExamRoom() {}

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<ExamSlot> getSlots() {
        if (slots == null) slots = new java.util.ArrayList<>();
        return slots;
    }

    public void setSlots(List<ExamSlot> slots) {
        this.slots = slots;
    }
}
