package com.example.exammonitor.controller;

import com.example.exammonitor.model.Student;
import com.example.exammonitor.repository.StudentRepository;
import com.example.exammonitor.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping

    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("students", studentService.searchStudents(keyword.trim()));
        } else {
            model.addAttribute("students", studentService.getAllStudents());
        }
        model.addAttribute("keyword", keyword);
        return "students/list";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute( "student", new Student());
        return "students/form";
    }

    @GetMapping("/edit/{studentId}")
    public String edit(@PathVariable String studentId, Model model) {
        Student student = studentService.getStudentById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with studentId: " + studentId));
        model.addAttribute("student", student);
        return "students/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Student student) {
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/delete/{studentId}")
    public String delete(@PathVariable String studentId) {
        studentService.deleteStudentById(studentId);
        return "redirect:/students";
    }
}

