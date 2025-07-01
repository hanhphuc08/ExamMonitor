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
    public String list(Model model) {
        model.addAttribute("students", studentService.getAllStudents());;
        return "students/list";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute( "student", new Student());
        return "students/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Student student) {
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        studentService.deleteStudentById(id);
        return "redirect:/students";
    }
}

