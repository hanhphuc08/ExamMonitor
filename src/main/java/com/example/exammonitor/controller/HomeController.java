package com.example.exammonitor.controller;

import com.example.exammonitor.service.StudentService;
import com.example.exammonitor.service.ExamAreaService;
import com.example.exammonitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ExamAreaService examAreaService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        // Tính toán thống kê thực tế
        long totalStudents = studentService.getAllStudents().size();
        long totalExamAreas = examAreaService.getAllExamAreas().size();
        long totalInvigilators = userService.getAllUsers().stream()
                .filter(user -> "INVIGILATOR".equals(user.getRole().toString()))
                .count();
        
        // Tính số ca thi hôm nay
        long todayExamSlots = examAreaService.getAllExamAreas().stream()
                .flatMap(area -> area.getFloors() != null ? area.getFloors().stream() : java.util.stream.Stream.empty())
                .flatMap(floor -> floor.getRooms() != null ? floor.getRooms().stream() : java.util.stream.Stream.empty())
                .flatMap(room -> room.getSlots() != null ? room.getSlots().stream() : java.util.stream.Stream.empty())
                .filter(slot -> LocalDate.now().equals(slot.getExamDate()))
                .count();

        // Thêm dữ liệu vào model
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("totalExamAreas", totalExamAreas);
        model.addAttribute("todayExamSlots", todayExamSlots);
        model.addAttribute("totalInvigilators", totalInvigilators);

        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
} 