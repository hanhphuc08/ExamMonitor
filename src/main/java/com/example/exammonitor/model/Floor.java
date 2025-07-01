package com.example.exammonitor.model;

import java.util.List;

public class Floor {
    private int floorNumber;
    private List<ExamRoom> rooms;

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public List<ExamRoom> getRooms() {
        return rooms;
    }

    public void setRooms(List<ExamRoom> rooms) {
        this.rooms = rooms;
    }
}
