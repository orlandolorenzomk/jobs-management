package com.kreyzon.jobsmanagement.controller;

import com.kreyzon.jobsmanagement.dto.InterviewDto;
import com.kreyzon.jobsmanagement.model.Interview;
import com.kreyzon.jobsmanagement.model.Recruiter;
import com.kreyzon.jobsmanagement.response.GenericResponse;
import com.kreyzon.jobsmanagement.service.InterviewService;
import com.kreyzon.jobsmanagement.service.RecruiterService;
import com.kreyzon.jobsmanagement.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/interview")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    private final RecruiterService recruiterService;

    @GetMapping("/search")
    public String showInterviewSearch(Model model) {
        List<Interview> interviewList = interviewService.findAll();
        model.addAttribute("interviewList", interviewList);
        return "interview/interview-search";
    }

    @GetMapping("/insert")
    public String showAddInterviewForm(Model model) {
        // Prepare necessary data for the form if needed

        // TODO Retrieve list of companies from CompanyService
        List<Recruiter> recruiterList = recruiterService.findAll();
        model.addAttribute("recruiterList", recruiterList);

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

    // Update Interview
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Interview interview = interviewService.findById(id);
        model.addAttribute("interview", interview);
        return "/interview/interview-update";
    }

    // Update Interview
    @PostMapping("/update")
    public String updateInterview(@ModelAttribute("interviewDto") InterviewDto interviewDto, RedirectAttributes redirectAttributes) {
        GenericResponse response = interviewService.updateInterview(interviewDto);

        if (response.getResult().equals(Constant.RESULT_OK)) {
            redirectAttributes.addFlashAttribute("successMessage", response.getMessage());
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", response.getMessage());
        }

        return "redirect:/interview/search";
    }

    // Delete Interview
    @GetMapping("/delete/{id}")
    public String deleteInterview(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        GenericResponse response = interviewService.deleteInterview(id);

        if (response.getResult().equals(Constant.RESULT_OK)) {
            redirectAttributes.addFlashAttribute("successMessage", response.getMessage());
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", response.getMessage());
        }

        return "redirect:/interview/search";
    }
}
