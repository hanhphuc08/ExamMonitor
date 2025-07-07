package com.example.exammonitor.model;

import java.util.List;

public class Floor {
    private int floorNumber;
    private List<ExamRoom> rooms;

<<<<<<< HEAD
=======
    public Floor() {}

>>>>>>> QA_branch
    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public List<ExamRoom> getRooms() {
<<<<<<< HEAD
=======
        if (rooms == null) rooms = new java.util.ArrayList<>();
>>>>>>> QA_branch
        return rooms;
    }

    public void setRooms(List<ExamRoom> rooms) {
        this.rooms = rooms;
    }
}
