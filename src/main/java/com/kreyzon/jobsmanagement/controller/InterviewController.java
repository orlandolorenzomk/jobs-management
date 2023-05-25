package com.kreyzon.jobsmanagement.controller;

import com.kreyzon.jobsmanagement.dto.InterviewDto;
import com.kreyzon.jobsmanagement.model.Interview;
import com.kreyzon.jobsmanagement.response.GenericResponse;
import com.kreyzon.jobsmanagement.service.InterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/interview")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    @GetMapping("/search")
    public String showInterviewSearch(Model model) {
        List<Interview> interviewList = interviewService.findAll();
        model.addAttribute("interviewList", interviewList);
        return "interview/interview-search";
    }

    @GetMapping("/insert")
    public String showAddInterviewForm(Model model) {
        // Prepare necessary data for the form if needed
        return "interview/interview-insert";
    }

    @PostMapping("/add")
    public String addInterview(@ModelAttribute InterviewDto interviewDto, RedirectAttributes redirectAttributes) {
        GenericResponse response = interviewService.addInterview(interviewDto);
        if (response.isResultNOK()) {
            redirectAttributes.addFlashAttribute("message", response.getMessage());
            return "redirect:/interview/add"; // Redirect back to the add form in case of error
        }
        return "redirect:/interview/search";
    }
}
