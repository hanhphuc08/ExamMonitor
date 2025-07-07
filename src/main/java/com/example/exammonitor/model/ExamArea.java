package com.example.exammonitor.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Document("exam_areas")
public class ExamArea {
    @Id
    private String id;
    private String name;
    private String description;
    private List<Floor> floors;

<<<<<<< HEAD
=======
    public ExamArea() {}

>>>>>>> QA_branch
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Floor> getFloors() {
<<<<<<< HEAD
=======
        if (floors == null) floors = new java.util.ArrayList<>();
>>>>>>> QA_branch
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }
}

