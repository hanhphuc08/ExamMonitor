package com.example.exammonitor.model;

import java.util.List;

public class Floor {
    private int floorNumber;
    private List<ExamRoom> rooms;

    public Floor() {}

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public List<ExamRoom> getRooms() {
        if (rooms == null) rooms = new java.util.ArrayList<>();
        return rooms;
    }

    public void setRooms(List<ExamRoom> rooms) {
        this.rooms = rooms;
    }
}
