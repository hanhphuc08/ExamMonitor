package com.example.exammonitor.controller;

import com.example.exammonitor.model.ExamArea;

import com.example.exammonitor.repository.ExamAreaRepository;
import com.example.exammonitor.service.ExamAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/examareas")
public class ExamAreaController {
    @Autowired
    private ExamAreaService examAreaService;
    @GetMapping
    public String list(@RequestParam(value = "keyword", required = false) String keyword,
                      @RequestParam(value = "searchField", required = false) String searchField,
                      Model model) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            if (searchField == null || searchField.equals("id")) {
                model.addAttribute("examareas", examAreaService.searchById(keyword.trim()));
            } else {
                model.addAttribute("examareas", examAreaService.searchByName(keyword.trim()));
            }
        } else {
            model.addAttribute("examareas", examAreaService.getAllExamAreas());
        }
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchField", searchField);
        return "examareas/list";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("examarea", new ExamArea());
        return "examareas/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        ExamArea examarea = examAreaService.getExamAreaById(id)
                .orElseThrow(() -> new RuntimeException("ExamArea not found with id: " + id));
        model.addAttribute("examarea", examarea);
        return "examareas/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute ExamArea examarea) {


        System.out.println("ExamArea nhận được: " + examarea);
        if (examarea.getFloors() != null) {
            for (int i = 0; i < examarea.getFloors().size(); i++) {
                System.out.println("  Floor " + i + ": " + examarea.getFloors().get(i));
            }
        }
        examAreaService.saveExamArea(examarea);
        return "redirect:/examareas";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        examAreaService.deleteExamAreaById(id);
        return "redirect:/examareas";
    }
}
