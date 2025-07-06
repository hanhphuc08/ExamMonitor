package com.example.exammonitor;

import org.springframework.boot.SpringApplication;

public class TestExamMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.from(ExamMonitorApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
